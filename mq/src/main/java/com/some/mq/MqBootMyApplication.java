package com.some.mq;

import com.some.mq.consume.StreamInputChannel;
import com.some.mq.producer.StreamOutputChannel;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.stream.annotation.EnableBinding;

@SpringBootApplication
@EnableBinding(value={StreamInputChannel.class, StreamOutputChannel.class})
public class MqBootMyApplication extends SpringBootServletInitializer{
	public static void main(String[] args) throws Exception {
		SpringApplication.run(MqBootMyApplication.class, args);

	}

}
