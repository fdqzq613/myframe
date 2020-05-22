package com.some.order.cancel;

import com.some.order.coordinator.ITccCancel;
import com.some.order.mq.vo.KcOrderVo;
import lombok.extern.slf4j.Slf4j;

/**
 * @description:
 * @vsersion: V1.0
 * @author: qzq
 * @date: 2020-05-22 15:19
 */
@Slf4j
public class OrderCancel implements ITccCancel {
    @Override
    public String getNo() {
        return "order";
    }

    @Override
    public void cancel(KcOrderVo kcOrderVo) {

        log.warn("订单取消：{}",kcOrderVo.getOrderNo());
    }
}
