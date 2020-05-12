package com.some.web.config;

import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DruidConfigurer {

	private static final String DB_PREFIX = "spring.datasource";

	@Bean
	public ServletRegistrationBean statViewServlet() {
		// 创建servlet注册实体
		ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new StatViewServlet(),
				"/druid/*");
		// 设置ip白名单
		servletRegistrationBean.addInitParameter("allow", "127.0.0.1");
		// 设置控制台管理用户
		servletRegistrationBean.addInitParameter("loginUsername", "qzq");
		servletRegistrationBean.addInitParameter("loginPassword", "123456");
		// 是否可以重置数据
		servletRegistrationBean.addInitParameter("resetEnable", "false");
		return servletRegistrationBean;
	}

	@Bean
	public FilterRegistrationBean statFilter() {
		// 创建过滤器
		FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(new WebStatFilter());
		// 设置过滤器过滤路径
		filterRegistrationBean.addUrlPatterns("/*");
		// 忽略过滤的形式
		filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
		return filterRegistrationBean;
	}
}
