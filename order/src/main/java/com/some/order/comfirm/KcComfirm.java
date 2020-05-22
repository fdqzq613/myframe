package com.some.order.comfirm;

import com.some.order.coordinator.ITccComfirm;
import lombok.extern.slf4j.Slf4j;

/**
 * @description:
 * @vsersion: V1.0
 * @author: qzq
 * @date: 2020-05-22 15:16
 */
@Slf4j
public class KcComfirm implements ITccComfirm {


    @Override
    public boolean comfirm() {
        log.info("库存确认");
        return true;
    }
}
