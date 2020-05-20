package com.some.kc;

import com.some.kc.consume.KcInputChannel;
import com.some.kc.producer.KcFinishOutputChannel;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.stream.annotation.EnableBinding;

@SpringBootApplication
@EnableBinding(value={KcInputChannel.class, KcFinishOutputChannel.class})
public class KcBootMyApplication extends SpringBootServletInitializer{
	public static void main(String[] args) throws Exception {
		SpringApplication.run(KcBootMyApplication.class, args);

	}

}
