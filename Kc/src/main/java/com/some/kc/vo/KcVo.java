package com.some.kc.vo;

import lombok.Data;

/**
 * @description: kc
 * @vsersion: V1.0
 * @author: qzq
 * @date: 2020-05-20 14:51
 */
@Data
public class KcVo {
    //最终库存数量
    private int num;
    //预扣剩余库存数量
    private int preNum;
}
