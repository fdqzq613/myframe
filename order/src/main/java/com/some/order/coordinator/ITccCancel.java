package com.some.order.coordinator;

import com.some.order.mq.vo.KcOrderVo;

/**
 * @description:
 * @vsersion: V1.0
 * @author: qzq
 * @date: 2020-05-22 14:24
 */
public interface ITccCancel {
     String getNo();
     void cancel(KcOrderVo kcOrderVo);
}
