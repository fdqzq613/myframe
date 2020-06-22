package com.some;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;


@SpringBootApplication
@EnableEurekaClient
public class GatewayBootMyApplication extends SpringBootServletInitializer{
//http://localhost:8682/api/test/feignGet
	public static void main(String[] args) throws Exception {
		SpringApplication.run(GatewayBootMyApplication.class, args);
		
	}

}
