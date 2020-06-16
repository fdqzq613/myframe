package com.some.gateway.core.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionWriter;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @description: RefreshRoutesEvent
 * @vsersion: V1.0
 * @author: qzq
 * @date: 2020-05-14 10:40
 */
@Slf4j
@Service
public class RefreshRoutesService implements ApplicationEventPublisherAware {

    @Autowired
    private RouteDefinitionWriter routeDefinitionWriter;
    private ApplicationEventPublisher publisher;

    public void test() {
        RouteDefinition definition = new RouteDefinition();
        PredicateDefinition predicate = new PredicateDefinition();
        Map<String, String> predicateParams = new HashMap<>(8);

        definition.setId("baiduRoute");
        predicate.setName("Path");
        //请替换成本地可访问的路径
        predicateParams.put("pattern", "/baidu");
        //请替换成本地可访问的路径
        predicateParams.put("pathPattern", "/baidu");
        predicate.setArgs(predicateParams);
        definition.setPredicates(Arrays.asList(predicate));
        //请替换成本地可访问的域名
        URI uri = UriComponentsBuilder.fromHttpUrl("http://www.baidu.com").build().toUri();
        definition.setUri(uri);
        addRoute(definition);
    }
    public void refresh() {
        this.publisher.publishEvent(new RefreshRoutesEvent(this));
    }
    public void addRoute(RouteDefinition definition){
        //RouteDefinitionWriter 用来实现路由的添加与删除
        routeDefinitionWriter.save(Mono.just(definition)).subscribe();
        this.publisher.publishEvent(new RefreshRoutesEvent(this));
    }

    public boolean updateRoute(RouteDefinition definition){
        routeDefinitionWriter.delete(Mono.just(definition.getId()));
        routeDefinitionWriter.save((Mono<RouteDefinition>) (Mono.just(definition)).subscribe());
        this.publisher.publishEvent(new RefreshRoutesEvent(this));
        return true;
    }
    public void removeRoute(String routeId){
        routeDefinitionWriter.delete(Mono.just(routeId));
        this.publisher.publishEvent(new RefreshRoutesEvent(this));
    }
    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.publisher = applicationEventPublisher;
    }
}
