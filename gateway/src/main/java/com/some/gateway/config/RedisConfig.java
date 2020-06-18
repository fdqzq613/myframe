package com.some.gateway.config;

import com.alibaba.fastjson.support.spring.FastJsonRedisSerializer;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;


@Configuration
@EnableCaching
public class RedisConfig extends CachingConfigurerSupport{



	   @Bean
	   @Primary
	    public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory rf) {
	        StringRedisTemplate redisTemplate = new StringRedisTemplate(rf);
	        redisTemplate.setConnectionFactory(rf);
	        //redis   开启事务
	        redisTemplate.setEnableTransactionSupport(true);
	        //hash  使用FastJson  的序列化
	        FastJsonRedisSerializer<Object> valueRedisSerializer=new FastJsonRedisSerializer<Object>(Object.class);
	        redisTemplate.setHashValueSerializer(valueRedisSerializer);
	        RedisSerializer<String> keyRedisSerializer = new StringRedisSerializer();
	        //StringRedisSerializer  key  序列化
	        redisTemplate.setHashKeySerializer(keyRedisSerializer);
	        //keySerializer  对key的默认序列化器。默认值是StringSerializer
	        redisTemplate.setKeySerializer(keyRedisSerializer);
	        //  valueSerializer
	        redisTemplate.setValueSerializer(valueRedisSerializer);
	        redisTemplate.afterPropertiesSet();

	        return redisTemplate;
	    }

	@Bean
	public CacheManager cacheManager(RedisTemplate<String, String> template) {

		// 基本配置
		RedisCacheConfiguration defaultCacheConfiguration =
				getRedisCacheConfigurationWithTtl(60*60*24*30,template);

		// 构建一个redis缓存管理器
		RedisCacheManager redisCacheManager =
				RedisCacheManager.RedisCacheManagerBuilder
						// Redis 连接工厂
						.fromConnectionFactory(template.getConnectionFactory())
						// 缓存配置
						.cacheDefaults(defaultCacheConfiguration)
						// 配置同步修改或删除 put/evict
						.transactionAware().withInitialCacheConfigurations(getRedisCacheConfigurationMap(template))
						.build();

		return redisCacheManager;
	}

	/**
	 * 设置过期时间
	 * @return
	 */
	private Map<String, RedisCacheConfiguration> getRedisCacheConfigurationMap(RedisTemplate<String, String> template) {
		Map<String, RedisCacheConfiguration> redisCacheConfigurationMap = new HashMap<>();
		redisCacheConfigurationMap.put("some", this.getRedisCacheConfigurationWithTtl(60,template));
		return redisCacheConfigurationMap;
	}

	private RedisCacheConfiguration getRedisCacheConfigurationWithTtl(Integer seconds,RedisTemplate<String, String> template) {
		RedisCacheConfiguration defaultCacheConfiguration =
				RedisCacheConfiguration
						.defaultCacheConfig()
						// 设置key为String
						.serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(template.getStringSerializer()))
						// 设置value 为自动转Json的Object
						.serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(template.getValueSerializer()))
						// 不缓存null
						.disableCachingNullValues();
						// 缓存数据过期时间
						//.entryTtl(Duration.ofSeconds(5));
						if(seconds>0){
							return defaultCacheConfiguration.entryTtl(Duration.ofSeconds(seconds));
						}

		return defaultCacheConfiguration;
	}

}
