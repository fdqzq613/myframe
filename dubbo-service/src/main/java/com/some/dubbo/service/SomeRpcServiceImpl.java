package com.some.dubbo.service;

import com.some.dubbo.rpc.SomeRpcService;

import org.apache.dubbo.config.annotation.DubboService;
import org.apache.dubbo.config.annotation.Service;

/**
 * @description:
 * @vsersion: V1.0
 * @author: qzq
 * @date: 2020-07-01 15:50
 */
@DubboService(
        version = "${some.service.version}",
        application = "${dubbo.application.id}",
        protocol = "${dubbo.protocol.id}",
        registry = "${dubbo.registry.id}"
)
public class SomeRpcServiceImpl implements SomeRpcService {


    @Override
    public String getName() {
        return "test";
    }

}
