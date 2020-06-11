package com.some.authclient.config;

import com.google.common.collect.Lists;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

/**
 * @description:
 * @vsersion: V1.0
 * @author: qzq
 * @date: 2020-06-11 16:37
 */
public class PermissionFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    private static final Map<RequestMatcher, Collection<ConfigAttribute>> urlMap = new HashMap<>();
    static {
        //测试用 可以从数据库获取
        AntPathRequestMatcher url = new AntPathRequestMatcher("/api/testResourcePower");
        urlMap.put(url,Lists.newArrayList(new SecurityConfig("ROLE_ADMIN")));
    }

    public PermissionFilterInvocationSecurityMetadataSource() {
    }

    /**
     * 转换权限列表
     */
    private Map<RequestMatcher, Collection<ConfigAttribute>> requestMatcherCollectionMap() {
        return urlMap;
    }


    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {

        final HttpServletRequest request = ((FilterInvocation) object).getRequest();
        for (Map.Entry<RequestMatcher, Collection<ConfigAttribute>> entry : requestMatcherCollectionMap().entrySet()) {
            if (entry.getKey().matches(request)) {
                return entry.getValue();
            }
        }
        return null;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return requestMatcherCollectionMap().values()
                .stream().flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return FilterInvocation.class.isAssignableFrom(clazz);
    }
}
