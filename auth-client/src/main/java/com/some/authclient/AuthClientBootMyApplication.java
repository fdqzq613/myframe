package com.some.authclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AuthClientBootMyApplication {
	//http://localhost:7589/swagger-ui.html
	public static void main(String[] args) throws Exception {
		SpringApplication.run(AuthClientBootMyApplication.class, args);
	}

}
