package com.some.example.cache;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;

import java.util.concurrent.TimeUnit;

import lombok.extern.slf4j.Slf4j;

/**
 * @description:
 * @vsersion: V1.0
 * @author: qzq
 * @date: 2020-06-19 17:54
 */
@Slf4j
public class GuavaCacheTest {
    public static void main(String[] args) {
        //数据加载
        CacheLoader cacheLoader = new CacheLoader<String, String>() {
            @Override
            public String load(String key) throws Exception {
                // 处理缓存键不存在缓存值时的处理逻辑
                return "no";
            }
        };
        LoadingCache<String, String> cache = CacheBuilder.newBuilder()
                //缓存池大小，在缓存项接近该大小时， Guava开始回收旧的缓存项
                .maximumSize(1000)
                //设置时间对象没有被读/写访问则对象从内存中删除(在另外的线程里面不定期维护)
                .expireAfterAccess(1000, TimeUnit.MILLISECONDS)
                // 设置缓存在写入之后 设定时间 后失效
                .expireAfterWrite(1, TimeUnit.DAYS)
                //移除监听器,缓存项被移除时会触发
                .removalListener(new RemovalListener<String, String>() {
                    @Override
                    public void onRemoval(RemovalNotification<String, String> rn) {
                        log.info("fallback url:{}缓存被移除",rn.getKey());
                    }
                })
                //开启Guava Cache的统计功能
                .recordStats()
                .build(cacheLoader);

        try {
            log.info(cache.get("ss"));
            cache.put("22","66");
            log.info(cache.get("22"));
            log.info(cache.stats().toString());
            Thread.sleep(1000*2);
            log.info(cache.get("22"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
