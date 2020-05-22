package com.some;

import com.some.order.mq.consume.KcFinishInputChannel;
import com.some.order.mq.producer.KcOutputChannel;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.stream.annotation.EnableBinding;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
@EnableBinding(value={KcFinishInputChannel.class, KcOutputChannel.class})
public class OrderBootMyApplication extends SpringBootServletInitializer{
	//http://localhost:8781/swagger-ui.html
	public static void main(String[] args) throws Exception {
		SpringApplication.run(OrderBootMyApplication.class, args);

	}

}
