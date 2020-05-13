package some.gateway.common;

import com.some.common.result.RespResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

/**
 * 
 * @version V1.0
 * @author qzq
 * @date   2020年5月7日
 */
@Component
public class TokenGatewayFilter implements GatewayFilter ,Ordered{

	/**
	 * @return
	 * @author qzq
	 * @date 2020年5月7日 上午10:46:53
	 */
	@Override
	public int getOrder() {
		
		return 0;
	}

	/**
	 * @param exchange
	 * @param chain
	 * @return
	 * @author qzq
	 * @date 2020年5月7日 上午10:46:53
	 */
	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		
		String token = null;
		//从header取
		token = exchange.getRequest().getHeaders().getFirst("Authorization");
		if(StringUtils.isEmpty(token)){
			//尝试从param取
			token = exchange.getRequest().getQueryParams().getFirst("token");
		}
		//尝试从cookies取
		if(StringUtils.isEmpty(token)){
			HttpCookie cookies = exchange.getRequest().getCookies().getFirst("token");
			if(cookies!=null){
				token  = cookies.getValue();
			}
		}
		
		if(StringUtils.isEmpty(token)){
	

			return responseFailRs(exchange, RespResult.create(401,"token参数未传入"));
		}

		//TODO 校验jwt token
		
		return chain.filter(exchange);
	}
	
	 private Mono<Void> responseFailRs(ServerWebExchange exchange, RespResult RespResult) {
	        ServerHttpResponse serverHttpResponse = exchange.getResponse();
	        serverHttpResponse.setStatusCode(HttpStatus.UNAUTHORIZED);
	        serverHttpResponse.getHeaders().setContentType(MediaType.APPLICATION_JSON_UTF8);
	        byte[] bytes = RespResult.toString().getBytes(StandardCharsets.UTF_8);
	        DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(bytes);
	        return serverHttpResponse.writeWith(Flux.just(buffer));
	    }

}
