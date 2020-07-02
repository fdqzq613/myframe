package com.some.dubbo.consumer;

import com.some.dubbo.rpc.SomeRpcService;

import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:
 * @vsersion: V1.0
 * @author: qzq
 * @date: 2020-07-01 16:16
 */
@RestController
public class TestController {
    //http://127.0.0.1:9893/getName
    @DubboReference( version = "${some.service.version}",
            application = "${dubbo.application.id}",
            protocol = "${dubbo.protocol.id}",
            cluster = "myFail",
            registry = "${dubbo.registry.id}")
    private SomeRpcService someRpcService;
    @RequestMapping("/getName")
    public String getName() {
        return someRpcService.getName();
    }
}
