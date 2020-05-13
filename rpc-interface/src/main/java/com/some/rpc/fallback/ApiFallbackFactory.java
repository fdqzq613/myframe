package com.some.rpc.fallback;

import com.some.common.result.RespResult;
import com.some.rpc.api.IApiService;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * 
 * @version V1.0
 * @author qzq
 * @date 2020年4月27日
 */
@Slf4j
@Component
public class ApiFallbackFactory implements FallbackFactory<IApiService> {

    private boolean isFirst = true;

	@Autowired()
	@Qualifier("apiFallback")
	private IApiService apiService;
	/**
	 * @param e
	 * @return
	 * @author qzq
	 * @date 2020年4月27日 下午2:10:40
	 */
	@Override
	public IApiService create(Throwable e) {

		if(isFirst){
			
			isFirst=false;
		}else{
			
			log.error("调用服务出错：{}",e.getMessage(),e);
		}
		return apiService;
	}

	@Bean("apiFallback")
	public IApiService createApiService(){
		return new IApiService(){

			@Override
			public RespResult<String> get() {
				return RespResult.create(906, "服务暂时不可用");
			}
			
		};
	}
	
}
