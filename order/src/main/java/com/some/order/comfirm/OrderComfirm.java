package com.some.order.comfirm;

import com.some.order.coordinator.ITccComfirm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @description: comfirm
 * @vsersion: V1.0
 * @author: qzq
 * @date: 2020-05-22 14:10
 */
@Component
@Slf4j
public class OrderComfirm implements ITccComfirm {


    @Override
    public boolean comfirm() {
        log.info("订单确认");
        return true;
    }
}
