package com.some.order.cache;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @description: cache
 * @vsersion: V1.0
 * @author: qzq
 * @date: 2020-05-15 15:43
 */
@Component
@Slf4j
public class KcCache {
    private final String KC_ZK_KEY = "KC_ZK_KEY";
    private final String SPILT = "::";
    @Autowired
    private RedisTemplate redisTemplate;

    public Integer getKc(String goodsNo){
        return (Integer) redisTemplate.opsForValue().get(KC_ZK_KEY+SPILT+goodsNo);
    }
    public void sub(String goodsNo){
        redisTemplate.opsForValue().increment(KC_ZK_KEY+SPILT+goodsNo,-1);
    }

    public void reflesh(){
        //一天有效
        redisTemplate.opsForValue().set(KC_ZK_KEY+SPILT+"1000",100,24, TimeUnit.HOURS);
    }

}
