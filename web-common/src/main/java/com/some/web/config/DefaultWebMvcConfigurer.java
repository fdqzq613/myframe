package com.some.web.config;

import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.some.web.vo.ConfigVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.math.BigInteger;
import java.nio.charset.Charset;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

	/**
	 * 改用fastjson
	 *
	 * @param converters
	 * @author qzq
	 */
	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		converters.clear();
		// JSON转换器
		converters.add(fastJsonHttpMessageConverter());
	}

	@Bean(name="fastJsonHttpMessageConverter")
	public FastJsonHttpMessageConverter fastJsonHttpMessageConverter() {
		FastJsonHttpMessageConverter fastJsonHttpMessageConverter = new FastJsonHttpMessageConverter();
		fastJsonHttpMessageConverter.setDefaultCharset(Charset.forName("UTF-8"));
		FastJsonConfig fastJsonConfig = new FastJsonConfig();
		fastJsonConfig.setCharset(Charset.forName("UTF-8"));
		SerializerFeature[] features = new SerializerFeature[] { SerializerFeature.QuoteFieldNames,
				SerializerFeature.WriteDateUseDateFormat, SerializerFeature.WriteBigDecimalAsPlain,
				// SerializerFeature.WriteNonStringValueAsString,
				SerializerFeature.WriteNullListAsEmpty, SerializerFeature.WriteMapNullValue,
				SerializerFeature.MapSortField, SerializerFeature.WriteNullListAsEmpty,
				SerializerFeature.WriteNullStringAsEmpty };
		fastJsonConfig.setSerializerFeatures(features);
		// 启用统一字符串 long类型在js端会截短
		SerializeConfig serializeConfig = SerializeConfig.globalInstance;
		serializeConfig.put(BigInteger.class, ToStringSerializer.instance);
		serializeConfig.put(Long.class, ToStringSerializer.instance);
		serializeConfig.put(Long.TYPE, ToStringSerializer.instance);
		//统一时间格式
		serializeConfig.put(LocalDateTime.class, LocalDateTimeSerializer.instance);
		serializeConfig.put(LocalDate.class, LocalDateSerializer.instance);
		fastJsonConfig.setSerializeConfig(serializeConfig);
		fastJsonHttpMessageConverter.setFastJsonConfig(fastJsonConfig);
		List<MediaType> supportedMediaTypes = new ArrayList<>();
		// 两种JSON都要支持
		supportedMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
		supportedMediaTypes.add(MediaType.APPLICATION_JSON);
		fastJsonHttpMessageConverter.setSupportedMediaTypes(supportedMediaTypes);
		return fastJsonHttpMessageConverter;
	}

}
