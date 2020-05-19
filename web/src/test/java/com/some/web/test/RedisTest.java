package com.some.web.test;

import com.some.WebBootMyApplication;
import com.some.web.cache.SomeCache;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

/**
 * @description: redis
 * @vsersion: V1.0
 * @author: qzq
 * @date: 2020-05-15 14:48
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {WebBootMyApplication.class})// 指定启动类
public class RedisTest {
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private SomeCache someCache;


    @Test
    public void test() {
        someCache.someById("555566666699");
        String key = "some.redis";
        String value = "go";
        redisTemplate.opsForValue().set("9999666", value);
        redisTemplate.opsForValue().set(key, value, 10, TimeUnit.SECONDS);
        System.out.println("1:" + redisTemplate.opsForValue().get(key));
        key = "6188899989";
        System.out.println(someCache.selectById(key));
        System.out.println(someCache.selectById(key));
        System.out.println(someCache.selectById(key));
        System.out.println( "templ:"+redisTemplate.opsForValue().get("user::"+key));
        key = "998889998";
        System.out.println(someCache.getUserMap(key).get("d"));
        System.out.println(someCache.getUserMap(key).get("d"));

        someCache.clear();


        redisTemplate.opsForHash().put("dd22",key,"66");
        System.out.println(redisTemplate.opsForHash().get("dd22",key));


    }
}
