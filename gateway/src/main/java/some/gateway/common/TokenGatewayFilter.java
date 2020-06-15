package some.gateway.common;

import com.some.common.result.RespResult;
import com.some.common.utils.AjaxUtils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.server.ServerWebExchange;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.GATEWAY_ROUTE_ATTR;

/**
 * 
 * @version V1.0
 * @author qzq
 * @date   2020年5月7日
 */
@Slf4j
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
		
		if(StringUtils.isEmpty(token)&&exchange.getRequest().getURI().toString().indexOf("login")==-1){

			if(!isAjax(exchange.getRequest())){


				//跳转到登录页面
				//return chain.filter(exchange.mutate().request(request).build());
				return 	exchange.getSession().flatMap(webSession -> {
					log.info("websession: {}", webSession.getId());
					ServerHttpRequest req = exchange.getRequest();
					ServerWebExchangeUtils.addOriginalRequestUrl(exchange, req.getURI());
					String newPath = "/api/login";
					ServerHttpRequest request = req.mutate().path(newPath).build();
					//1.记录原路径，用于认证成功后跳转--2.跳转回来后，gateway作为资源服务器判断资源的权限
					exchange.getAttributes().put(ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR, request.getURI());
					webSession.getAttributes().put(webSession.getId(),exchange.getRequest().getURI());
					return chain.filter(exchange.mutate().request(request).build());
				});
			}else {
				return responseFailRs(exchange, RespResult.create(401, "token参数未传入"));
			}
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

	public static boolean isAjax(ServerHttpRequest request){
		return  	request.getHeaders().getFirst("X-Requested-With") != null && request.getHeaders().getFirst("X-Requested-With").equalsIgnoreCase("XMLHttpRequest");
	}
	@Value("${security.oauth2.client.client-id}")
	private String clientId;
	@Value("${security.oauth2.client.client-secret}")
	private String clientSecret;
	//http://localhost:7585/oauth/authorize?client_id=client_2&redirect_uri=http://localhost:7589/callback&response_type=code&scope=all&state=zTf6yW
	@GetMapping("/callback")
	public String callback(String code) {
		String url = "http://localhost:7585/oauth/token";
		Map<String,String> paramsMap = new HashMap<>();
		paramsMap.put("grant_type","authorization_code");
		paramsMap.put("code",code);
		paramsMap.put("client_id",clientId);
		paramsMap.put("client_secret",clientSecret);
		//验证后 跳转地址 前台用
		paramsMap.put("redirect_uri","http://localhost:7589/api/testPower");
		String rs = null;
		//String rs = HttpClientUtils.httpPost(url,paramsMap);
		return rs;
	}
}
