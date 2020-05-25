package com.some.docker.core.controller;

import com.some.common.result.RespResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:
 * @vsersion: V1.0
 * @author: qzq
 * @date: 2020-05-25 14:18
 */
@RestController
public class TestController {
    @GetMapping( "/test")
    public RespResult<String> test(){
        return RespResult.create("test");
    }
}
