package com.some.rpc.api;

import com.some.common.result.RespResult;
import com.some.rpc.fallback.ApiFallbackFactory;
import feign.hystrix.FallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 
 * @version V1.0
 * @author qzq
 * @date   2020年4月27日
 */
@FeignClient(name  = "some-api-service",fallbackFactory = ApiFallbackFactory.class)
public interface IApiService {

	
	@RequestMapping(value = "/test/get")
	RespResult<String> get();
}
