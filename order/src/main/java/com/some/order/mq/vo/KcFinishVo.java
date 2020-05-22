package com.some.order.mq.vo;

import lombok.Data;

/**
 * @description: finish
 * @vsersion: V1.0
 * @author: qzq
 * @date: 2020-05-20 14:18
 */
@Data
public class KcFinishVo {
    private int code;
    private long orderNo;
    private long goodsNo;
    private int num;
    private String msg;
}
