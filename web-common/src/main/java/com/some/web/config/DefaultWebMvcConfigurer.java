package com.some.web.config;

import com.some.web.vo.ConfigVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 
 * 功能说明:
 *
 * @version V1.0
 * @author qzq
 * @since JDK1.8
 */
@Configuration
public class DefaultWebMvcConfigurer implements WebMvcConfigurer {
	private static final Logger logger = LoggerFactory.getLogger(DefaultWebMvcConfigurer.class);
	@Autowired
	private ConfigVo configVo;

	/**
	 * 添加自定义入参校验
	 * 
	 * @param argumentResolvers
	 * @author qzq
	 */
	// @Override
	// public void addArgumentResolvers(List<HandlerMethodArgumentResolver>
	// argumentResolvers) {
	// argumentResolvers.add(new DefaultMethodArgumentResolver(true));
	// }
	/**
	 * 默认允许跨域
	 * 
	 * @param registry
	 * @author qzq
	 */
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**").allowedHeaders("*").allowedMethods("*").allowedOrigins("*");
	}

	/**
	 * 静态资源路径配置
	 * 
	 * @param registry
	 * @author qzq
	 */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
	}

}
