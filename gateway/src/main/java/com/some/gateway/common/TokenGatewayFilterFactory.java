package com.some.gateway.common;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;

/**
 * @description: token过滤器factory
 * @vsersion: V1.0
 * @author: qzq
 * @date: 2020-05-14 11:20
 */
@Component
public class TokenGatewayFilterFactory extends AbstractGatewayFilterFactory<Object> {
    @Override
    public GatewayFilter apply(Object config) {
        return new TokenGatewayFilter();
    }
}
