package com.some.order.handler;

import com.some.order.coordinator.IHandler;
import com.some.order.coordinator.OrderCoordinator;
import com.some.order.mq.producer.SendService;
import com.some.order.mq.vo.KcOrderVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @description:
 * @vsersion: V1.0
 * @author: qzq
 * @date: 2020-05-22 15:01
 */
@Component
public class KcHandler implements IHandler {
    @Autowired
    private SendService sendService;
    @Override
    public void doHandle(KcOrderVo kcOrderVo, OrderCoordinator orderCoordinator) {
        //发送库存预扣消息处理
        sendService.send(kcOrderVo);
    }
}
