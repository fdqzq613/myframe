package com.some.web.cache;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @description: cache
 * @vsersion: V1.0
 * @author: qzq
 * @date: 2020-05-15 15:43
 */
@Component
@Slf4j
public class SomeCache {

    /**
     * redis默认存储key为 cacheNames::+key 如 "user::" + key)
     * @param id
     * @return
     */
    @Cacheable(cacheNames="user", key="#id")
    public String selectById(String id) {
        log.info("获取id:{}的缓存",id);
        return id+":go";
    }

    @Cacheable(cacheNames="some", key="#id")
    public String someById(String id) {
        log.info("获取some:id:{}的缓存",id);
        return id+":go";
    }

    @Cacheable(cacheNames="userMap", key="#id")
    public Map<String,String> getUserMap(String id) {
        log.info("获取id:{}的缓存",id);
        Map<String,String> o = new HashMap<>();
        o.put("d","测试");
        o.put("p","22");
        return o;
    }

    @CacheEvict(cacheNames="userMap",allEntries=true)
    public void clear() {
        log.info("缓存清除");
    }
}
