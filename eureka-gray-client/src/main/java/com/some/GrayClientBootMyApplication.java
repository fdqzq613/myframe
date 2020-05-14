package com.some;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
public class GrayClientBootMyApplication extends SpringBootServletInitializer{
	public static void main(String[] args) throws Exception {
		SpringApplication.run(GrayClientBootMyApplication.class, args);

	}

}
