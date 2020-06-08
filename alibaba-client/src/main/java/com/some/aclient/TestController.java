package com.some.aclient;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
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

    @RequestMapping("/getSome")
    public String getSome() {
        return "非资源点";
    }
//http://localhost:8868/test/getNacos
    @RequestMapping("/getNacos") //同时配置 只认fallback
    @SentinelResource(value="getNacos",fallback = "fallback",blockHandler = "getNacosblock")
    public String getNacos() {
        log.info("获取服务配置");
        return testService.getNacos();
    }


    @RequestMapping("/getNacos3") //全局fallback
    @SentinelResource(value="getNacos3",defaultFallback = "fallback",fallbackClass  = {MyGlobalFallback.class})
    public String getNacos3(String p) {
        log.info("获取服务配置3");
        return testService.getNacos();
    }


    @RequestMapping("/getNacos2")
    @SentinelResource(value="getNacos2",blockHandler = "getNacosblock")
    public String getNacos2() {
        log.info("获取服务配置2");
        return testService.getNacos();
    }

    /**
     * 限流后返回
     * @return
     */
    public String getNacosblock(BlockException ex){
        return "该服务繁忙，请稍后再试";
    }
    public String fallback(){
        return "服务不可用，降级";
    }
}
