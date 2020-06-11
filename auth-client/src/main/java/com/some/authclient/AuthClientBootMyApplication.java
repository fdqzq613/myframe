package com.some.authclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
public class AuthClientBootMyApplication {
	//http://localhost:7585/oauth/authorize?response_type=token&client_id=client_2&redirect_uri=http://localhost:7589/api/testPower&scope=all
	public static void main(String[] args) throws Exception {
		SpringApplication.run(AuthClientBootMyApplication.class, args);
	}

}
