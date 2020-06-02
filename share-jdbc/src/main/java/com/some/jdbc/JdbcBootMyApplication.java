package com.some.jdbc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JdbcBootMyApplication {
	//http://localhost:8986/swagger-ui.html
	public static void main(String[] args) throws Exception {
		SpringApplication.run(JdbcBootMyApplication.class, args);
	}

}
