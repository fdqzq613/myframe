package com.some.kc.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PerformanceInterceptor;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@MapperScan("com.some.kc.mapper")
@EnableTransactionManagement
public class MybatisPlusConfig {
    /**
     * mybatis-plus SQL执行效率插件【生产环境可以关闭】
     */
    @Bean
    @Profile({"dev","test","gray"})
    public PerformanceInterceptor performanceInterceptor() {
    	PerformanceInterceptor performanceInterceptor = new PerformanceInterceptor();
    	performanceInterceptor.setFormat(true);
    	performanceInterceptor.setWriteInLog(true);
    	performanceInterceptor.setMaxTime(5000);
        return performanceInterceptor;
    }
   
    /**
     * mybatis-plus分页插件<br>
     * 文档：http://mp.baomidou.com<br>
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
    	 PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
         return paginationInterceptor;
    }

}
