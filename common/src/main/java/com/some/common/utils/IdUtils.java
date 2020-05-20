package com.some.common.utils;

import com.some.common.id.SnowFlake;

/**
 * @description: id
 * @vsersion: V1.0
 * @author: qzq
 * @date: 2020-05-20 18:07
 */
public class IdUtils {
    private static SnowFlake snowFlake = new SnowFlake(1, 1);
    public static Long getId(){
        return snowFlake.nextId();
    }
}
