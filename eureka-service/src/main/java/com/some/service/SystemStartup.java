package com.some.service;


import lombok.extern.slf4j.Slf4j;
import lombok.extern.slf4j.XSlf4j;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SystemStartup {


	@EventListener
	public void init(ContextRefreshedEvent contextRefreshedEvent)  {
		log.info("开始启动eureka-service系统。。。");

		log.info("eureka-service系统启动完成");
	}
}
