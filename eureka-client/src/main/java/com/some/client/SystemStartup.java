package com.some.client;


import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SystemStartup {


	@EventListener
	public void init(ContextRefreshedEvent contextRefreshedEvent)  {
		log.info("开始启动eureka-client系统。。。");



		long start = System.currentTimeMillis();
		log.info("eureka-client系统启动完成，耗时：{}",(System.currentTimeMillis()-start)/1000);
	}
}
