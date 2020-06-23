package com.some;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.netflix.turbine.EnableTurbine;

@SpringBootApplication
@EnableEurekaServer
@EnableHystrixDashboard //http://localhost:8085/hystrix 输入http://localhost:8086/hystrix.stream//单体应用监控
@EnableTurbine  //http://localhost:8085/hystrix   http://localhost:8085/turbine.stream//集群应用监控
public class ServiceBootMyApplication extends SpringBootServletInitializer{
	public static void main(String[] args) throws Exception {

		SpringApplication.run(ServiceBootMyApplication.class, args);
	}

}
