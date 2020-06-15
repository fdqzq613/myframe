package com.some.rpc.common;

import com.some.common.utils.UserUtils;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import feign.RequestInterceptor;
import feign.RequestTemplate;

/**
 * token默认传递下去
 * @version V1.0
 * @author qzq
 */
@Configuration
public class FeignConfiguration implements RequestInterceptor {

	@Override
	public void apply(RequestTemplate requestTemplate) {
		String token = UserUtils.getToken();
		if(StringUtils.isEmpty(token)){
			ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
			if (attributes != null) {
				HttpServletRequest request = attributes.getRequest();
				token =  request.getParameter("token");
			}
		}
		if(!StringUtils.isEmpty(token)){

			requestTemplate.header("Authorization", token);
		}

	}

}
