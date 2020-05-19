package com.some.mq.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
//http://localhost:8681/swagger-ui.html
@Configuration
@EnableSwagger2
public class SwaggerConfig {
		@Bean
	    public Docket api() {
	        return new Docket(DocumentationType.SWAGGER_2)
	                .select()  // 选择那些路径和api会生成document
	                .apis(RequestHandlerSelectors.basePackage("com.some")) // 对所有api进行监控
	                .paths(PathSelectors.any()) // 对所有路径进行监控
	                .build()
	                .apiInfo(apiInfo());
	    }
	 
	    private ApiInfo apiInfo() {
	        ApiInfo apiInfo = new ApiInfoBuilder().title("MQ API")
	                .description("MQ api")
	                .version("1.0")
	                .build();
	        return apiInfo;
	    }
}
