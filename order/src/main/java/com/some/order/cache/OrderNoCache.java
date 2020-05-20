package com.some.order.cache;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @description: orderno
 * @vsersion: V1.0
 * @author: qzq
 * @date: 2020-05-20 14:34
 */
@Component
@Slf4j
public class OrderNoCache {
    @Autowired
    private RedisTemplate redisTemplate;
    private final String ORDER_KEY = "ORDER_KEY";
    private final String SPILT = "::";
    public String getOrderNo(){
        return null;
    }
    private String getKey(long orderNo){
        return ORDER_KEY+SPILT+orderNo;
    }
    public String getGoodsNo(long orderNo){
        return (String) redisTemplate.opsForValue().get(getKey(orderNo));
    }
    public boolean containsKey(long orderNo){

        return redisTemplate.hasKey(getKey(orderNo));
    }
    public Integer setStatus(long orderNo,Integer status){
        return (Integer) redisTemplate.opsForValue().getAndSet(getKey(orderNo),status);
    }
}
