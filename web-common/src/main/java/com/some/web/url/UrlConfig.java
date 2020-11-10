package com.some.web.url;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import lombok.Data;

/**
 * @description:
 * @vsersion: V1.0
 * @author: qzq
 * @date: 2020-11-05 11:23
 */
@Component
@Configuration
@Data
public class UrlConfig {
    @Value("${config.self.staticPath:/}")
    private  String staticPath;
    @Value("${server.servlet.context-path:/}")
    private String contextPath;
    @Value("${server.port:8080}")
    private String port;
    /**
     * 项目前缀前路径
     */
    @Value("${config.self.ctx:#{null}}")
    private String ctx;
}
