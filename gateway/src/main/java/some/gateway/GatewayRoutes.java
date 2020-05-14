package some.gateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.RewritePathGatewayFilterFactory;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.GatewayFilterSpec;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.gateway.route.builder.UriSpec;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import some.gateway.common.DevRoutePredicateFactory;
import some.gateway.common.TokenGatewayFilter;
import some.gateway.common.UrlGatewayFilterFactory;

import java.util.function.Function;

@Configuration
public class GatewayRoutes {
	@Autowired
	private TokenGatewayFilter tokenGatewayFilter;
	//http://localhost:8682/some/
	@Bean
	public RouteLocator routeLocator(RouteLocatorBuilder builder) {
		//重定向替换
		UrlGatewayFilterFactory.Config config = new UrlGatewayFilterFactory.Config();
        config.setRegexp("/some/(?<segment>.*)");
        config.setReplacement("/${segment}");
        GatewayFilter urlGatewayFilterFactory = new UrlGatewayFilterFactory()
                .apply(config);
        Function<GatewayFilterSpec, UriSpec> fn = gatewayFilterSpec -> gatewayFilterSpec
				.filter(tokenGatewayFilter).filter(urlGatewayFilterFactory)
        		.hystrix(hysConfig -> hysConfig
                        .setName("some")
                        .setFallbackUri("forward:/fallback"));
        
		String url = "http://localhost:8081/some/";

        return builder.routes()
                //basic proxy
                .route("some",p -> p.path("/some/**")
                        //导入配置
                        .filters(fn)
                        .uri(url)
                ).build();
	}
	//http://localhost:8682/api/test/get
	@Bean
	public RouteLocator apiRouteLocator(RouteLocatorBuilder builder) {
		//重定向替换
		RewritePathGatewayFilterFactory.Config config = new RewritePathGatewayFilterFactory.Config();
        config.setRegexp("/api/(?<segment>.*)");
        config.setReplacement("/${segment}");
        GatewayFilter rewritePathGatewayFilter = new RewritePathGatewayFilterFactory()
                .apply(config);
        Function<GatewayFilterSpec, UriSpec> fn = gatewayFilterSpec -> gatewayFilterSpec.filter(tokenGatewayFilter).filter(rewritePathGatewayFilter)
        		.hystrix(hysConfig -> hysConfig
                        .setName("api")
                        .setFallbackUri("forward:/fallback"));

        return builder.routes()
                //basic proxy
                .route("api",p -> p.path("/api/**")
                        //导入配置
                        .filters(fn)
                        .uri("lb://some-api-service")
                ).build();
	}


	
	@Bean
	public DevRoutePredicateFactory initMyRoutePredicateFactory(){
		return new DevRoutePredicateFactory(DevRoutePredicateFactory.Config.class);
	}

}
