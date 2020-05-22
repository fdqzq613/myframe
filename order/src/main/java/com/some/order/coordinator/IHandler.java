package com.some.order.coordinator;

import com.some.order.mq.vo.KcOrderVo;

public interface IHandler {
    public void doHandle(KcOrderVo kcOrderVo,OrderCoordinator orderCoordinator);
}
