package com.some.example.cache;



import com.google.common.cache.RemovalNotification;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.RemovalCause;
import com.github.benmanes.caffeine.cache.RemovalListener;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

import lombok.extern.slf4j.Slf4j;

/**
 * @description:
 * @vsersion: V1.0
 * @author: qzq
 * @date: 2020-06-19 17:56
 */
@Slf4j
public class CaffeineTest {
    public static void main(String[] args) {
        Cache<String, String> manualCache = Caffeine.newBuilder()
                //缓存池大小，在缓存项接近该大小时， Guava开始回收旧的缓存项
                .maximumSize(1000)
                //设置时间对象没有被读/写访问则对象从内存中删除(在另外的线程里面不定期维护)
                .expireAfterAccess(1000, TimeUnit.MILLISECONDS)
                // 设置缓存在写入之后 设定时间 后失效
                .expireAfterWrite(1, TimeUnit.DAYS)
                //移除监听器,缓存项被移除时会触发
                .removalListener(new RemovalListener<String, String>() {


                    @Override
                    public void onRemoval(@Nullable String key, @Nullable String value, @NonNull RemovalCause removalCause) {
                        log.info("缓存被移除,key:{},value:{}",key,value);
                    }
                })
                //.build(key -> loadData(key));
                .build();

        String key = "name1";
// 根据key查询一个缓存，如果没有返回NULL
        String value = manualCache.getIfPresent(key);
        log.info(value);
// 根据Key查询一个缓存，如果没有调用loadData方法，并将返回值保存到缓存。
// 如果该方法返回Null则manualCache.get返回null，如果该方法抛出异常则manualCache.get抛出异常
        value = manualCache.get(key, k -> loadData(k));
        log.info(value);
// 将一个值放入缓存，如果以前有值就覆盖以前的值
        manualCache.put(key, "22");
// 删除一个缓存
        manualCache.invalidate(key);
        try {
            manualCache.put(key, "99");
            log.info(manualCache.getIfPresent(key));
            Thread.sleep(1000*2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String value1 = manualCache.getIfPresent(key);
        log.info(value1);
       // @NonNull ConcurrentMap<String, String> map = manualCache.asMap();
    }

    public static String loadData(String key) {
        return key + "value";
    }
}
