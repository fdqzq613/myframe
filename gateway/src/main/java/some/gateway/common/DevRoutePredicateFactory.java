package some.gateway.common;

import org.springframework.cloud.gateway.handler.predicate.AbstractRoutePredicateFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.web.server.ServerWebExchange;

import java.util.List;
import java.util.function.Predicate;

/**
 * 自定义断言,切换到指定断言ip
 * @version V1.0
 * @author qzq
 * @date   2020年3月31日
 */
public class DevRoutePredicateFactory extends AbstractRoutePredicateFactory<DevRoutePredicateFactory.Config> {




	/**
	 * @param configClass
	 */
	public DevRoutePredicateFactory(Class<Config> configClass) {
		super(configClass);
	}


	/**
	 * @param config
	 * @return
	 * @author qzq
	 * @date 2020年3月31日 下午4:05:14
	 */
	@Override
	public Predicate<ServerWebExchange> apply(Config config) {

		return exchange -> {
            //判断header里有放token
            HttpHeaders headers = exchange.getRequest().getHeaders();
            List<String> header = headers.get(config.getHeaderName());
			String value = exchange.getRequest().getHeaders().getFirst(config.getHeaderName());
			//开启开发环境
            if("true".equals(value)){

			}
            return "true".equals(value);
        };
	}


	public static class Config {

        private String headerName;

        public String getHeaderName() {
            return headerName;
        }

        public void setHeaderName(String headerName) {
            this.headerName = headerName;
        }
    }
}
