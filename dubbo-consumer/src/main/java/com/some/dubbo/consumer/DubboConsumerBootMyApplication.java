package com.some.dubbo.consumer;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DubboConsumerBootMyApplication {
	public static void main(String[] args)  {
		SpringApplication.run(DubboConsumerBootMyApplication.class, args);
	}

}
