package com.some.web.db;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@Aspect
@Order(-1)
public class DataSourceAspect {
    public DataSourceAspect(){
        logger.debug("选择数据源---");
    }
    private static final Logger logger = LoggerFactory.getLogger(DataSourceAspect.class);
    @Pointcut("@within(com.yoya.movie.ext.db.DataSource) || @annotation(com.yoya.movie.ext.db.DataSource)")
    public void pointCut(){
        logger.debug("选择数据源---");
    }

    @Before("pointCut() && @annotation(dataSource)")
    public void doBefore(DataSource dataSource){
        logger.debug("选择数据源---"+dataSource.value().getValue());
        DataSourceContextHolder.setDataSource(dataSource.value().getValue());
    }

    @After("pointCut()&& @annotation(dataSource)")
    public void doAfter(DataSource dataSource){
        logger.debug("清除数据源---"+dataSource.value().getValue());
        DataSourceContextHolder.remove();
    }
}
