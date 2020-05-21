package com.some;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class ServiceBootMyApplication extends SpringBootServletInitializer{
	public static void main(String[] args) throws Exception {

		SpringApplication.run(ServiceBootMyApplication.class, args);
	}

}