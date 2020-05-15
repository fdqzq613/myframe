package some.gateway.common;

import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.LoadBalancerClientFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @description: LoadBalancerClientFilter
 * @vsersion: V1.0
 * @author: qzq
 * @date: 2020-05-14 14:59
 */
@Component
public class MyLoadBalancerClientFilter extends LoadBalancerClientFilter {
    public static ThreadLocal<ServerWebExchange> serverWebExchangeHolder = new ThreadLocal<>();

    public MyLoadBalancerClientFilter(LoadBalancerClient loadBalancer) {
        super(loadBalancer);
    }


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        serverWebExchangeHolder.set(exchange);
        return super.filter(exchange, chain);
    }
}
