package com.some.aclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

//http://localhost:8848
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class AlibabaClientBootMyApplication extends SpringBootServletInitializer{
	public static void main(String[] args) throws Exception {
		SpringApplication.run(AlibabaClientBootMyApplication.class, args);

	}

}
