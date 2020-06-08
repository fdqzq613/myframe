package com.some.authclient.controller;

import com.some.common.utils.HttpClientUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @description:
 * @vsersion: V1.0
 * @author: qzq
 * @date: 2020-06-05 18:07
 */
@RestController
public class CallbackController {
    @Value("${security.oauth2.client.clientId}")
    private String clientId;
    @Value("${security.oauth2.client.clientSecret}")
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

        String rs = HttpClientUtils.httpPost(url,paramsMap);
        return rs;
    }
}
