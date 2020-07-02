package com.some.dubbo;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableDubbo
public class DubboServiceBootMyApplication {
	public static void main(String[] args)  {
		SpringApplication.run(DubboServiceBootMyApplication.class, args);
	}

}
