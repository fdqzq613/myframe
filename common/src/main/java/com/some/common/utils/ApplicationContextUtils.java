package com.some.common.utils;

import org.springframework.beans.BeansException;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * 获取spring bean 辅助类
 * 
 * 功能说明:
 *
 * @Copyright: 优芽网络科技-版权所有
 * @version V1.0
 * @author qzq
 * @date   2017年7月5日
 * @since  JDK1.6
 */
@Component
public class ApplicationContextUtils implements ApplicationContextAware, CommandLineRunner   {

	 private static ApplicationContext applicationContext;

	    @Override
	    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
	        if(ApplicationContextUtils.applicationContext == null) {
	        	ApplicationContextUtils.applicationContext = applicationContext;
	        }
	    }

	    public static ApplicationContext getApplicationContext() {
	        return applicationContext;
	    }

	    public static <T> T  getBean(String name){
	        return (T) getApplicationContext().getBean(name);
	    }
 
	    public static <T> T getBean(Class<T> clazz){
	        return getApplicationContext().getBean(clazz);
	    }

	    public static <T> T getBean(String name,Class<T> clazz){
	        return getApplicationContext().getBean(name, clazz);
	    }

		/**
		 * @param args
		 * @throws Exception
		 * @author qzq
		 * @date 2017年12月12日 下午3:58:23
		 */
		@Override
		public void run(String... args) throws Exception {
			
		}


}
