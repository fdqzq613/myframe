package com.some.client.aop;

import com.some.common.constants.SystemEnum;
import com.some.common.exception.RespException;
import com.some.common.exception.UnauthorizedException;
import com.some.common.result.RespResult;
import com.some.common.utils.TokenUtils;
import com.some.common.utils.UserUtils;
import com.some.web.utils.CookieUtils;

import javax.jws.soap.SOAPBinding;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import lombok.extern.slf4j.Slf4j;

/**
 * @author qzq
 * @version 1.0
 * @since 1.0
 */
@Component
@Slf4j
public class TokenPowerInterceptor extends HandlerInterceptorAdapter {

    /**
     * 默认不需要权限的url--模糊匹配
     */
    protected static String[] noAuthUrls = new String[]{

            "/getToken", "/error", "/csrf", "/swagger", "/login", "/p/","/hystrix","/actuator"};


    /**
     * 自定义是否需要权限判断
     *
     * @param request
     * @return
     * @author qzq
     */
    protected boolean defMyNoPowerUrls(HttpServletRequest request) {
        return false;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        if (isExistsNoAuthUrl(request)) {
            return true;
        }
        if (!checkAuth(request, response)) {
            throw new UnauthorizedException("用户令牌认证不通过！");
        }
        return true;

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        UserUtils.reset();
    }

    /**
     * 不需要权限url， 模糊匹配
     *
     * @return
     * @author qzq
     */
    protected String[] getNoAuthUrls() {
        return noAuthUrls;
    }

    protected boolean isExistsNoAuthUrl(HttpServletRequest request) {
        String[] myNoAuthUrls = getNoAuthUrls();
        for (String noAuthUrl : myNoAuthUrls) {

            if (request.getRequestURI().toLowerCase().indexOf(noAuthUrl.toLowerCase()) != -1) {
                return true;
            }
        }
        if (defMyNoPowerUrls(request)) {
            return true;
        }
        //不需要权限路径 正则匹配  如 "^(test1|test).*"
        String noPowerUrl = "";
        if (!StringUtils.isBlank(noPowerUrl) && request.getRequestURI().matches(noPowerUrl)) {
            return true;
        }

        if(1==1){
            return true;
        }
        return false;
    }

    protected boolean checkAuth(HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            String userId = request.getHeader("userId");
            //gateway已经认证过了  直接赋值userId
            if(!StringUtils.isEmpty(userId)){
                UserUtils.setUserId(userId,null,request);
                return true;
            }
            // 优先从参数token获取
            String tokenStr = request.getParameter("token");
            if (StringUtils.isEmpty(tokenStr)) {

                // 从请求头获取 Authorization bear token
                String authorization = request.getHeader("Authorization");
                if (StringUtils.isEmpty(authorization)) {
                    authorization = request.getHeader("authorization");
                }
                if (!StringUtils.isEmpty(authorization)) {
                    String[] authorizations = authorization.split("%20");
                    if (authorizations.length == 0) {
                        throw new UnauthorizedException();
                    }
                    // 兼容
                    if (authorizations.length == 1) {
                        authorizations = authorization.split(" ");
                    }
                    if (authorizations.length == 1) {
                        // 支持直接写token
                        tokenStr = authorizations[0];
                    } else if (authorizations[0].toLowerCase().startsWith("bearer")) {
                        tokenStr = authorizations[1];
                        if (StringUtils.isEmpty(tokenStr)) {
                            throw new UnauthorizedException();
                        }
                    } else {
                        log.warn("不支持的认证格式：{}" , authorizations[0]);
                        throw new UnauthorizedException("不支持的认证格式：" + authorizations[0]);
                    }
                } else {
                    // 最后如果还是为空则从cookie获取
                    tokenStr = CookieUtils.getCookie(request, "token");
                }

            }
            if (StringUtils.isEmpty(tokenStr)) {
                throw new UnauthorizedException();
            }
            return checkToken(tokenStr, request);

        } catch (Exception e) {
            if (e instanceof RespException) {
                RespException respException = (RespException) e;
                throw e;
            }
			log.error(e.getMessage(), e);
            return false;
        }

    }

    /**
     * 校验令牌
     *
     * @param token
     * @param request
     * @return
     * @author qzq
     * @date 2018年6月12日 上午11:49:16
     */
    protected boolean checkToken(String token, HttpServletRequest request) {
        return realCheckToken(token, getSecret(request), request);
    }

    protected String getSecret(HttpServletRequest request) {
        return "{bcrypt}" + new BCryptPasswordEncoder().encode("123456");
    }

    protected boolean realCheckToken(String token, String appsecret, HttpServletRequest request) {
//		Map<String, Object> body = new JwtAccessTokenConverter().decode(token);
//		// 为空则token过期或者错误 直接返回登录
//		if (body == null) {
//			throw new UnauthorizedException("token过期或者错误");
//		}
//		String userId = (String) body.get("user_id");
//		// 通过保存用户id
//		UserUtils.setUserId(userId,token,request);
        //校验jwt token
        SystemEnum.codesEnum code = TokenUtils.getInstance().isValidDefault(token);
        if(code.getCode()!=SystemEnum.codesEnum.SUCCESS.getCode()){
            throw new UnauthorizedException(code.getMsg());
        }
        String userId = TokenUtils.getInstance().getUserId(token);
        UserUtils.setUserId(userId,token,request);
        return true;
    }
}