
package com.some.web.aop;


import com.some.common.exception.RespException;
import com.some.common.exception.UnauthorizedException;
import com.some.web.utils.CookieUtils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class PowerInterceptor extends HandlerInterceptorAdapter {
    protected static final Logger logger = LoggerFactory.getLogger(PowerInterceptor.class);

    /**
     * 默认不需要权限的url--模糊匹配
     */
    protected static String[] noAuthUrls = new String[]{

            "/getToken", "/error", "/csrf", "/swagger", "/login", "/p/", "/test"};


    /**
     * 自定义是否需要权限判断
     *
     * @param request
     * @return
     * @author qzq
     * @date 2019年9月9日 下午4:50:45
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
        return false;
    }

    protected boolean checkAuth(HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
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
                        logger.warn("不支持的认证格式：" + authorizations[0]);
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
            logger.error(e.getMessage(), e);
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
        return "";
    }

    protected boolean realCheckToken(String token, String appsecret, HttpServletRequest request) {
        return true;
    }
}