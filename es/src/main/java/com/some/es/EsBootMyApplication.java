package com.some.es;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EsBootMyApplication {
	//http://localhost:8585/swagger-ui.html
	public static void main(String[] args) throws Exception {
		SpringApplication.run(EsBootMyApplication.class, args);
	}

}
