package com.some.nacos;

import com.alibaba.nacos.api.annotation.NacosInjected;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.annotation.NacosConfigListener;
import com.alibaba.nacos.api.exception.NacosException;
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
@RequestMapping("/config")
@RefreshScope
@Slf4j
public class ConfigController {


    @Value("${some.def:ff}")
    private String useLocalCache;

    @Value("${some.def2:pp}")
    private String useLocalCache2;
    @Value("${some.serviceSome:pp}")
    private String serviceSome;

    @RequestMapping("/get")
    public String get() {
        return useLocalCache;
    }
//http://localhost:8848/config/getNacos
    @RequestMapping("/getNacos")
    public String getNacos() {
        return useLocalCache2+":"+serviceSome;
    }



}
