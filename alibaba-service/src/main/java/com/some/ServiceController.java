package com.some;

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
@RequestMapping("/service")
@RefreshScope
public class ServiceController {

    @Value("${some.serviceSome}")
    private String serviceSome;


//http://localhost:8858/service/getNacos
    @RequestMapping("/getNacos")
    public String getNacos() {
        return serviceSome;
    }
}
