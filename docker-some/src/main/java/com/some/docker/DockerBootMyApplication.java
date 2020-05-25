package com.some.docker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
//http://localhost:8082
@SpringBootApplication
public class DockerBootMyApplication extends SpringBootServletInitializer{
	public static void main(String[] args) throws Exception {
		SpringApplication.run(DockerBootMyApplication.class, args);

	}

}
