package com.some.web.db;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@Aspect
@Order(-1)
public class DataSourceAspect {
	public DataSourceAspect(){
		log.debug("选择数据源---");
	}
    @Pointcut("@within(com.some.web.db.DataSource) || @annotation(com.some.web.db.DataSource)")
    public void pointCut(){
        log.debug("选择数据源---");
    }

    @Before("pointCut() && @annotation(dataSource)")
    public void doBefore(DataSource dataSource){
    	log.debug("选择数据源---"+dataSource.value().getValue());
        DataSourceContextHolder.setDataSource(dataSource.value().getValue());
    }

    @After("pointCut()")
    public void doAfter(){
        DataSourceContextHolder.clear();
    }
}
