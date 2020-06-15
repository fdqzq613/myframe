package some.gateway.common;

import com.some.common.result.RespResult;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import java.nio.charset.StandardCharsets;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 
 * @version V1.0
 * @author qzq
 * @date   2020年5月7日
 */
@Slf4j
@Component
public class TokenGatewayFilter implements GatewayFilter ,Ordered{
	@Autowired
	private RedisTemplate redisTemplate;
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
	 * test --http://localhost:8682/api/test/feignGet?token=1233
	 *  ---未登录测试 http://localhost:8682/api/test/feignGet
	 * @param exchange
	 * @param chain
	 * @return
	 * @author qzq
	 * @date 2020年5月7日 上午10:46:53
	 */
	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

		String url = exchange.getRequest().getURI().getPath();
//		//登录后回调
//		if(url.indexOf("callback")!=-1){
//			return 	exchange.getSession().flatMap(webSession -> {
//				ServerHttpRequest req = exchange.getRequest();
//				ServerWebExchangeUtils.addOriginalRequestUrl(exchange, req.getURI());
//				String redirect_uri = webSession.getAttribute("redirect_uri");
//				String access_token = req.getQueryParams().getFirst("access_token");
//				ServerHttpRequest request = req.mutate().header("Authorization",access_token).path(redirect_uri).build();
//				exchange.getAttributes().put(ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR, request.getURI());
//				return chain.filter(exchange.mutate().request(request).build());
//			});
//		}
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
		
		if(StringUtils.isEmpty(token)&&exchange.getRequest().getURI().toString().indexOf("login")==-1){

			if(!isAjax(exchange.getRequest())){


				//跳转到登录页面
				//return chain.filter(exchange.mutate().request(request).build());
				return 	exchange.getSession().flatMap(webSession -> {
					log.info("websession: {}", webSession.getId());
					ServerHttpRequest req = exchange.getRequest();
					ServerWebExchangeUtils.addOriginalRequestUrl(exchange, req.getURI());
					//统一登录页面--采用简化模式，直接返回access_token
					String newPath = "/api/login?redirect_uri=http://localhost:8682"+exchange.getRequest().getURI().getPath();
					ServerHttpRequest request = req.mutate().path(newPath).build();
					//1.记录原路径，用于认证成功后跳转--2.跳转回来后，gateway作为资源服务器判断资源的权限
					exchange.getAttributes().put(ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR, request.getURI());
					webSession.getAttributes().put("redirect_uri",exchange.getRequest().getURI().getPath());
					return chain.filter(exchange.mutate().request(request).build());
				});
			}else {
				return responseFailRs(exchange, RespResult.create(401, "token参数未传入"));
			}
		}

		//TODO 校验jwt token  资源权限等
		
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

	public static boolean isAjax(ServerHttpRequest request){
		return  	request.getHeaders().getFirst("X-Requested-With") != null && request.getHeaders().getFirst("X-Requested-With").equalsIgnoreCase("XMLHttpRequest");
	}

}
