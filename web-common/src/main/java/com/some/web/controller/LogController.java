package com.some.web.controller;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;
import com.some.web.vo.ConfigVo;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/log")
public class LogController {
	private static final Logger logger = LoggerFactory.getLogger(LogController.class);
	@Autowired
	private ConfigVo configVo;
	/**
	 * 只针对 logback /log/logLevel?logLevel=debug
	 * @param logLevel
	 * @param packageName
	 * @return
	 * @throws Exception
	 * @author qzq
	 */
	@ApiOperation(value = "动态切换日志级别", notes = "动态切换日志级别", httpMethod = "GET")
	@RequestMapping(value = "/logLevel")
    public String logLevel( String logLevel,  String packageName) throws Exception {
        //trace --> debug --> info --> warn --> error -->fatal
		if(packageName==null){
			packageName="com";
		}
        LoggerContext loggerContext =(LoggerContext)LoggerFactory.getILoggerFactory();
        loggerContext.getLogger(packageName).setLevel(Level.valueOf(logLevel));
        logger.info("切换日志级别为：{}",logLevel);
        return "success--level change--"+logLevel;
   }
}
