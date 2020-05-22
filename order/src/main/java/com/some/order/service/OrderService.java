package com.some.order.service;

import com.some.common.result.RespResult;
import com.some.common.utils.IdUtils;
import com.some.order.cache.KcCache;
import com.some.order.cache.OrderNoCache;
import com.some.order.constants.IOrderStatus;
import com.some.order.coordinator.OrderCoordinator;
import com.some.order.coordinator.OrderCoordinatorHolder;
import com.some.order.domain.Order;
import com.some.order.mapper.OrderMapper;
import com.some.order.mq.producer.SendService;
import com.some.order.mq.vo.KcOrderVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @description: order
 * @vsersion: V1.0
 * @author: qzq
 * @date: 2020-05-20 11:43
 */
@Service
public class OrderService {
    @Autowired
    private KcCache kcCache;
    @Autowired
    private OrderNoCache orderNoCache;



    public long getOrderNo(){
        long orderNo =  IdUtils.getId();
        orderNoCache.setStatus(orderNo, IOrderStatus.orderEnum.ORDER_ONLY_CREATE_NO.getCode());
        return orderNo;
    }

    /**
     * 支付成功回调处理；1库存最终扣除;2设置订单完成状态
     * @return
     */
    public RespResult<String>  payCallback(long orderNo){
        return RespResult.create();
    }
    /**
     * 下单-预扣库存
     * @param kcOrderVo
     */
    public RespResult<String> preOrder(KcOrderVo kcOrderVo){
        //获取商品id
        Integer status = orderNoCache.getStatus(kcOrderVo.getOrderNo());
        //判断orderNo有没消费
        if(status==null){
            return RespResult.create(906,"订单号不存在或已经消费");
        }
        //先在缓存判断库存
        Integer num= kcCache.getKc(kcOrderVo.getGoodsNo());
        if(num==null||num<=0){
            return RespResult.create(909,"该商品已经没有库存");
        }

        //启动事务
        OrderCoordinator orderCoordinator = new OrderCoordinator();
        new Thread(new Runnable() {
            @Override
            public void run() {
                OrderCoordinatorHolder.put(kcOrderVo.getOrderNo(),orderCoordinator);
                orderCoordinator.orderTry(kcOrderVo);
                orderCoordinator.doHandle(kcOrderVo);
            }
        }).start();
        orderCoordinator.checkFinish();
        return RespResult.create("预扣消息已经发送");
    }



    public RespResult<String> pay(String orderNo){

        return RespResult.create("");
    }

}
