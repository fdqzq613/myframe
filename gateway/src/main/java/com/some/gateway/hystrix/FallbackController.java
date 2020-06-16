package com.some.gateway.hystrix;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.GATEWAY_ROUTE_ATTR;

/**
 * 
 * @version V1.0
 * @author qzq
 * @date   2019年8月6日
 */
@RestController
public class FallbackController {
	 private static final Logger logger = LoggerFactory.getLogger(FallbackController.class);
	 private boolean first = true;
	@RequestMapping("/fallback")
    public Mono<String> fallback(ServerWebExchange exchange) {
		if(first){
			Route route = exchange.getAttribute(GATEWAY_ROUTE_ATTR);
			logger.warn("该服务暂时不可用:{}",route.getUri().getPath());
			first=false;
		}
        return Mono.just("该服务暂时不可用");
    }
}
