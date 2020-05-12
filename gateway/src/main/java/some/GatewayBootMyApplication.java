package some;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;


@SpringBootApplication
@EnableEurekaClient
public class GatewayBootMyApplication extends SpringBootServletInitializer{ 

	public static void main(String[] args) throws Exception {
		SpringApplication.run(GatewayBootMyApplication.class, args);
		
	}

}
