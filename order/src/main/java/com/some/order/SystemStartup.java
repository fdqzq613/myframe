package com.some.order;


import com.some.order.cache.KcCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
@Component
@Slf4j
public class SystemStartup  {
	@Autowired
	private KcCache kcCache;
	/**
	 * 初始化
	 * 
	 * @author qzq
	 * @throws Exception 
	 */
	@EventListener
	public void init(ContextRefreshedEvent contextRefreshedEvent)  {
	  log.info("初始化商品库存");
		kcCache.reflesh();
	}
}
