package com.some.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
public class AuthBootMyApplication {
	//http://localhost:7585/swagger-ui.html
	public static void main(String[] args) throws Exception {
		SpringApplication.run(AuthBootMyApplication.class, args);
	}

}
