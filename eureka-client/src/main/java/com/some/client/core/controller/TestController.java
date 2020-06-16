package com.some.client.core.controller;

import com.some.common.result.RespResult;
import com.some.common.utils.UserUtils;
import com.some.rpc.api.IApiService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description: test
 * @vsersion: V1.0
 * @author: qzq
 * @date: 2020-05-13 14:05
 */
@RestController
@RequestMapping("/test")
@Api(value = "test接口", tags = "test接口")
@Slf4j
public class TestController {

    @Autowired
    private IApiService apiService;
    @ApiOperation(value = "注册到eureka的服务", notes = "注册到eureka的服务", httpMethod = "GET")
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public RespResult<String> get() {
        log.info("test：user_id:{}",UserUtils.getUserId());
        return RespResult.create("test--user_id:"+UserUtils.getUserId());
    }


    @ApiOperation(value = "feign调用", notes = "feign调用", httpMethod = "GET")
    @RequestMapping(value = "/feignGet", method = RequestMethod.GET)
    public RespResult<String> feignGet() {
        log.info("gateway传递的：user_id:{}",UserUtils.getUserId());
        return apiService.get();
    }
}
