package com.some.gateway.hystrix;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;

import com.some.common.result.RespResult;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.ServerWebExchangeDecorator;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.GATEWAY_ROUTE_ATTR;

/**
 * 
 * @version V1.0
 * @author qzq
 * @date   2019年8月6日
 */
@RestController
@Slf4j
public class FallbackController {
	 private static final Logger logger = LoggerFactory.getLogger(FallbackController.class);
	 private LoadingCache<String, String> cache;
	 @PostConstruct
	 public void init(){
		 cache = initCache();
	 }
	@RequestMapping("/fallback")
    public Mono<RespResult> fallback(ServerWebExchange exchange) {
		try {
			ServerWebExchange delegate = ((ServerWebExchangeDecorator) exchange).getDelegate();
			String oriUrl = delegate.getRequest().getPath().toString();
			if(!"1".equals(cache.get(oriUrl))){
				Exception e = exchange.getAttribute(ServerWebExchangeUtils.HYSTRIX_EXECUTION_EXCEPTION_ATTR);
				//Route route = exchange.getAttribute(GATEWAY_ROUTE_ATTR);
				logger.warn("该服务暂时不可用:{}",oriUrl,e);
				cache.put(oriUrl,"1");
			}
		} catch (ExecutionException e) {
			log.error(e.getMessage(),e);
		}
		return Mono.just(RespResult.create(969,"该服务暂时不可用"));
    }

    private LoadingCache<String, String> initCache(){
		CacheLoader cacheLoader = new CacheLoader<String, String>() {
			@Override
			public String load(String key) throws Exception {
				// 处理缓存键不存在缓存值时的处理逻辑
				return "";
			}
		};
		LoadingCache<String, String> cache = CacheBuilder.newBuilder()
				//缓存池大小，在缓存项接近该大小时， Guava开始回收旧的缓存项
				.maximumSize(1000)
				//设置时间对象没有被读/写访问则对象从内存中删除(在另外的线程里面不定期维护)
				.expireAfterAccess(10, TimeUnit.MINUTES)
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
		return cache;
	}

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
			Thread.sleep(1000*2);
			log.info(cache.get("22"));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
