package com.some.auth.handler;

import com.some.auth.vo.SsoUser;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;


/**
 * 自定义accessToken添加额外信息
 */
@Component
public class CustomTokenEnhancer implements TokenEnhancer {

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {

        final Map<String, Object> additionalInformation = new HashMap<>();
        SsoUser user = (SsoUser) authentication.getUserAuthentication().getPrincipal();
        //把用户的主键userId放进去
        additionalInformation.put("userId", user.getId());
        additionalInformation.put("username", user.getUsername());
        additionalInformation.put("authorities", user.getAuthorities());
        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInformation);

        additionalInformation.put("organization", authentication.getName());

        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInformation);
        return accessToken;
    }
}
