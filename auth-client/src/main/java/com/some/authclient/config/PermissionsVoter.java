package com.some.authclient.config;

import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.Objects;

import io.micrometer.core.instrument.util.StringUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * @description:
 * @vsersion: V1.0
 * @author: qzq
 * @date: 2020-06-11 16:44
 */
@Slf4j
public class PermissionsVoter implements AccessDecisionVoter<Object> {

    @Override
    public boolean supports(ConfigAttribute attribute) {
        return Objects.nonNull(attribute.getAttribute());
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }

    @Override
    public int vote(Authentication authentication, Object object, Collection<ConfigAttribute> attributes) {

        if (CollectionUtils.isEmpty(attributes)) {
            return ACCESS_DENIED;
        }
        // 用户授权的权限
        Collection<? extends GrantedAuthority> grantedAuthorities;
        if (Objects.isNull(authentication)
                || CollectionUtils.isEmpty(grantedAuthorities = extractAuthorities(authentication))
                || Objects.isNull(object)) {

            log.info("user no authentication!");
            return ACCESS_DENIED;
        }
        for (GrantedAuthority grantedAuthority : grantedAuthorities) {

            String authority;
            if (StringUtils.isNotBlank(authority = grantedAuthority.getAuthority())
                    && match(authority, attributes)) {
                return ACCESS_GRANTED;
            }
        }
        return ACCESS_DENIED;
    }

    private boolean match(String authority, Collection<ConfigAttribute> attributes) {

        for (ConfigAttribute configAttribute : attributes) {
            String attribute;
            if (StringUtils.isNotBlank(attribute = configAttribute.getAttribute())
                    && attribute.equals(authority)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取用户权限列表
     */
    Collection<? extends GrantedAuthority> extractAuthorities(Authentication authentication) {
        return authentication.getAuthorities();
    }
}
