package com.some.kc.consume;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rabbitmq.client.Channel;
import com.some.common.exception.RespException;
import com.some.common.utils.IdUtils;
import com.some.kc.cache.KcCache;
import com.some.kc.domain.KcLog;
import com.some.kc.domain.OrderDetail;
import com.some.kc.mapper.KcLogMapper;
import com.some.kc.mapper.OrderDetailMapper;
import com.some.kc.producer.SendService;
import com.some.kc.vo.KcFinishVo;
import com.some.kc.vo.KcOrderVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.transaction.SystemException;
import javax.transaction.Transaction;
import javax.transaction.TransactionManager;
import javax.transaction.Transactional;
import java.io.IOException;
import java.time.LocalDateTime;

/**
 * 接收消息
 *
 * @author qzq
 * @version V1.0
 * @date 2020年4月13日
 */
@Service
@Slf4j
public class KcService {
    @Autowired
    private SendService sendService;
    @Autowired
    private KcCache kcCache;
    @Autowired
    private OrderDetailMapper orderDetailMapper;
    @Autowired
    private KcLogMapper kcLogMapper;
    @Autowired
    private PlatformTransactionManager transactionManager;


    /**
     * 订单预扣库存--还没支付
     *
     * @param kcOrderVo
     * @param channel
     * @param deliveryTag
     */
    @StreamListener(KcInputChannel.INPUT)
    public void onReceiver(KcOrderVo kcOrderVo, @Header(AmqpHeaders.CHANNEL) Channel channel,
                           @Header(AmqpHeaders.DELIVERY_TAG) Long deliveryTag) {
        //手动事务
        TransactionStatus transactionStatus = null;
        try {
            log.info("接收到减少库存消息:{}", kcOrderVo.getGoodsNo());
            //根据订单号加锁
            //幂等性判断
            // 数据库流水表判断有没有这个订单号
            KcLog queryKcLog = new KcLog();
            queryKcLog.setOrderNo(kcOrderVo.getOrderNo());
            QueryWrapper<KcLog> queryWrapper = new QueryWrapper<>(queryKcLog);
            int count = kcLogMapper.selectCount(queryWrapper);
            if (count > 0) {
                //已经处理过 直接结束
                log.warn("出现重复订单:{}，跳过", kcOrderVo.toString());
                return;
            }

            //根据订单号加分布式锁
            //数据库操作
            //获取库存数量 cas更新
            //自旋  或者采用分布式锁，锁住商品号实现
            int times = 0;
            DefaultTransactionDefinition def = new DefaultTransactionDefinition();
            def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);//新发起一个事务
            transactionStatus = transactionManager.getTransaction(def);
            while (true) {
                times++;
                //预扣的剩余库存数
                Integer lockNum = orderDetailMapper.getLockNum(kcOrderVo.getGoodsNo());
                KcFinishVo kcFinishVo = new KcFinishVo();
                BeanUtils.copyProperties(kcOrderVo, kcFinishVo);
                if (lockNum == null || lockNum <= 0) {
                    //发送库存扣除失败消息
                    kcFinishVo.setCode(906);
                    kcFinishVo.setMsg("没有库存");
                    sendService.sendPreLock(kcFinishVo);
                    break;
                }

                int newNum = lockNum - kcOrderVo.getNum();
                //数据库操作
                String sql = "update t_kc set pre_num=? where goods_no=? and pre_num=?";
                boolean execSql = true;

                int row = orderDetailMapper.updateLockNum(newNum, lockNum, LocalDateTime.now(), kcOrderVo.getGoodsNo());
                if (row > 0) {
                    kcFinishVo.setCode(200);

                    //更新缓存
                    //有库存则进行预扣
                    kcCache.sub(kcOrderVo.getGoodsNo(), kcOrderVo.getNum());
                    //记录流水
                    KcLog kcLog = new KcLog();
                    kcLog.setId(IdUtils.getId());
                    BeanUtils.copyProperties(kcOrderVo, kcLog);
                    kcLog.setOpType(1);
                    kcLog.setKcType(0);
                    kcLogMapper.insert(kcLog);
                    //锁定库存成功
                    sendService.sendPreLock(kcFinishVo);
                    log.info("库存预扣成功，剩余锁定库存:{}", newNum);
                    break;
                }
                //超过次数超时返回
                if (times > 100) {
                    //发送库存扣除失败消息
                    kcFinishVo.setCode(907);
                    kcFinishVo.setMsg("库存扣除超时");
                    sendService.sendPreLock(kcFinishVo);
                    break;
                }
            }
            transactionManager.commit(transactionStatus);
        } catch (Exception e) {
            log.error("减少锁定库存失败,进行回滚操作", e);
            if (transactionStatus != null) {
                transactionManager.rollback(transactionStatus);

            }
            //TODO 插入异常日志

        } finally {
            try {
                //手动模式 一定要确认
                channel.basicAck(deliveryTag, false);//手动确认
            } catch (IOException e) {
                log.error("手动确认失败", e);
            }
        }


    }


}
