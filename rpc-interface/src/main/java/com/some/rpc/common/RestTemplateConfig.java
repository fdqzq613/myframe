package com.some.rpc.common;

import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.support.ResponseEntityDecoder;
import org.springframework.cloud.openfeign.support.SpringDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.web.client.RestTemplate;

import feign.codec.Decoder;

/**
 * @description:
 * @vsersion: V1.0
 * @author: qzq
 * @date: 2020-06-15 11:06
 */
@Configuration
@DependsOn("fastJsonHttpMessageConverter")
public class RestTemplateConfig {

    @Autowired
    private FastJsonHttpMessageConverter fastJsonHttpMessageConverter;
    @Bean
    @LoadBalanced
    public RestTemplate getRestTemplate()
    {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().clear();
        restTemplate.getMessageConverters().add(fastJsonHttpMessageConverter);
        return restTemplate;
    }

    @Bean
    public Decoder feignDecoder() {
        return new ResponseEntityDecoder(new SpringDecoder(feignHttpMessageConverter()));
    }

    public ObjectFactory<HttpMessageConverters> feignHttpMessageConverter() {
        final HttpMessageConverters httpMessageConverters = new HttpMessageConverters(fastJsonHttpMessageConverter);
        return new ObjectFactory<HttpMessageConverters>() {
            @Override
            public HttpMessageConverters getObject() throws BeansException {
                return httpMessageConverters;
            }
        };
    }
}
