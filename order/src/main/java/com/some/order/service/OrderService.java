package com.some.order.service;

import com.some.common.result.RespResult;
import com.some.common.utils.IdUtils;
import com.some.order.cache.KcCache;
import com.some.order.cache.OrderNoCache;
import com.some.order.constants.IOrderStatus;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @description: order
 * @vsersion: V1.0
 * @author: qzq
 * @date: 2020-05-20 11:43
 */
public class OrderService {
    @Autowired
    private KcCache kcCache;
    private OrderNoCache orderNoCache;

    public long getOrderNo(){
        long orderNo =  IdUtils.getId();
        orderNoCache.setStatus(orderNo, IOrderStatus.orderEnum.ORDER_ONLY_CREATE_NO.getCode());
        return orderNo;
    }
    /**
     * 下单-预扣库存
     * @param orderNo
     */
    public RespResult<String> preOrder(long orderNo){
        //获取商品id
        String goodsNo = orderNoCache.getGoodsNo(orderNo);
        //判断orderNo有没消费
        if(goodsNo==null){
            return RespResult.create(906,"订单号已经消费");
        }
        //
        Integer num= kcCache.getKc(goodsNo);
        if(num!=null&&num>0){
            //有库存则进行预扣
            kcCache.sub(goodsNo);
        }
        //发送库存预扣消息处理
        return RespResult.create("预扣成功");
    }

    public RespResult<String> pay(String orderNo){

        return RespResult.create("");
    }

}
