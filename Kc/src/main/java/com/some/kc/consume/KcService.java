package com.some.kc.consume;

import com.rabbitmq.client.Channel;
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

import java.io.IOException;

/**
 * 接收消息
 * @version V1.0
 * @author qzq
 * @date   2020年4月13日
 */
@Service
@Slf4j
public class KcService {
	@Autowired
	private SendService sendService;

	/**
	 * 订单预扣库存--还没支付
	 * @param kcOrderVo
	 * @param channel
	 * @param deliveryTag
	 */
	@StreamListener(KcInputChannel.INPUT)
	public void onReceiver(KcOrderVo kcOrderVo, @Header(AmqpHeaders.CHANNEL) Channel channel,
						   @Header(AmqpHeaders.DELIVERY_TAG) Long deliveryTag){
		log.info("接收到减少库存消息:{}",kcOrderVo.getGoodsNo());
		//幂等性判断
		//TODO 数据库流水表判断有没有这个订单号
		String hadSql = "select count(1) from t_kc_log where orderNo=?";
		boolean had = false;
		if(had){
			//已经处理过 直接结束
		   log.warn("出现重复消息:{}，跳过",kcOrderVo.toString());
			return ;
		}
		//根据订单号加分布式锁
		//数据库操作
		//获取库存数量 cas更新
		//自旋  或者采用分布式锁，锁住商品号实现
		int times = 0;
		while(true){
			times++;
			//预扣的剩余库存数
			int preNum = 10;
			KcFinishVo kcFinishVo = new KcFinishVo();
			BeanUtils.copyProperties(kcOrderVo,kcFinishVo);
			if(preNum<=0){
				//发送库存扣除失败消息
				kcFinishVo.setCode(906);
				sendService.send(kcFinishVo);
				break;
			}
			int newNum = preNum-kcOrderVo.getNum();
			//TODO 数据库操作
			String sql = "update t_kc set pre_num=? where goods_no=? and pre_num=?";
			boolean execSql = true;

			if(execSql){
				kcFinishVo.setCode(200);
				sendService.send(kcFinishVo);
				break;
			}
			//超过次数超时返回
			if(times>100){
				//发送库存扣除失败消息
				kcFinishVo.setCode(907);
				sendService.send(kcFinishVo);
				break;
			}
		}

		try {
			channel.basicAck(deliveryTag, false);//手动确认
		} catch (IOException e) {
			log.error("手动确认失败",e);
		}

	}







}
