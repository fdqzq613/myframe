package com.some.web.init;

import com.some.web.vo.ConfigVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 
 * @version V1.0
 * @author qzq
 * @date 2019年12月16日
 */
@Configuration
@EnableConfigurationProperties(ConfigVo.class)
public class SomeAutoConfiguration implements DisposableBean{
	private static final Logger logger = LoggerFactory.getLogger(SomeAutoConfiguration.class);
	public  SomeAutoConfiguration(){
		System.out.println("create");
	}
	@Autowired
	private ConfigVo configVo;

	@Bean
	@ConditionalOnMissingBean(DefaultInit.class)
	public DefaultInit DefaultInit() {
		DefaultInit defaultInit = new DefaultInit(configVo);
		return defaultInit;
	}

	/**
	 * @throws Exception
	 * @author qzq
	 * @date 2019年12月16日 下午2:30:40
	 */
	@Override
	public void destroy() throws Exception {
		logger.info("框架销毁");
	}
}
