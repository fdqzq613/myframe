package com.some.zipkin;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:
 * @vsersion: V1.0
 * @author: qzq
 * @date: 2020-05-27 14:36
 */
@RestController
@RequestMapping("/test")
@Slf4j
public class TestController {
@Autowired
 private TestService testService;


//http://localhost:8999/test/getNacos
    @RequestMapping("/getNacos")
    public String getNacos() {
        log.info("zipkin获取服务配置");
        return testService.getNacos();
    }
}
