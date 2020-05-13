package some.gateway.common;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.http.server.reactive.ServerHttpRequest;

import java.util.Arrays;
import java.util.List;

import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.*;

/**
 * @description: url跳转filter工厂
 * @vsersion: V1.0
 * @author: qzq
 * @date: 2020-05-08 11:25
 */
public class UrlGatewayFilterFactory  extends AbstractGatewayFilterFactory<UrlGatewayFilterFactory.Config> {

    public static final String REGEXP_KEY = "regexp";
    public static final String REPLACEMENT_KEY = "replacement";

    public UrlGatewayFilterFactory() {
        super(UrlGatewayFilterFactory.Config.class);
    }

    @Override
    public List<String> shortcutFieldOrder() {
        return Arrays.asList(REGEXP_KEY, REPLACEMENT_KEY);
    }

    @Override
    public GatewayFilter apply(UrlGatewayFilterFactory.Config config) {
        String replacement = config.replacement.replace("$\\", "$");
        return (exchange, chain) -> {
            ServerHttpRequest req = exchange.getRequest();
            addOriginalRequestUrl(exchange, req.getURI());
            String path = req.getURI().getRawPath();
            String newPath = path.replaceAll(config.regexp, replacement);

            ServerHttpRequest request = req.mutate()
                    .path(newPath)
                    .build();

            exchange.getAttributes().put(GATEWAY_REQUEST_URL_ATTR, request.getURI());
            //重定义路由
            Route route = exchange.getAttribute(GATEWAY_ROUTE_ATTR);
            Route newRoute = Route.async().asyncPredicate(route.getPredicate()).filters(route.getFilters()).id(route.getId()).order(route.getOrder())
                    .uri(route.getUri().toString()+newPath).build();
            exchange.getAttributes().put(GATEWAY_ROUTE_ATTR, newRoute);

            return chain.filter(exchange.mutate().request(request).build());
        };
    }

    public static class Config {
        private String regexp;
        private String replacement;

        public String getRegexp() {
            return regexp;
        }

        public UrlGatewayFilterFactory.Config setRegexp(String regexp) {
            this.regexp = regexp;
            return this;
        }

        public String getReplacement() {
            return replacement;
        }

        public UrlGatewayFilterFactory.Config setReplacement(String replacement) {
            this.replacement = replacement;
            return this;
        }
    }
}
