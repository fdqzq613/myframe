package com.some.order.service;

import com.some.common.result.RespResult;
import com.some.common.utils.IdUtils;
import com.some.order.cache.KcCache;
import com.some.order.cache.OrderNoCache;
import com.some.order.constants.IOrderStatus;
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
    @Autowired
    private SendService sendService;

    public long getOrderNo(){
        long orderNo =  IdUtils.getId();
        orderNoCache.setStatus(orderNo, IOrderStatus.orderEnum.ORDER_ONLY_CREATE_NO.getCode());
        return orderNo;
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
        //发送库存预扣消息处理
        sendService.send(kcOrderVo);
        return RespResult.create("预扣消息已经发送");
    }

    public RespResult<String> pay(String orderNo){

        return RespResult.create("");
    }

}
