package com.some.gateway.common;

import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.RoundRobinRule;
import com.netflix.loadbalancer.Server;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @description: RoundRobinRule
 * @vsersion: V1.0
 * @author: qzq
 * @date: 2020-05-14 14:40
 */
@Component
public class MyRoundRobinRule extends RoundRobinRule {
    @Override
    public Server choose(Object key) {
        return super.choose(key);
    }

    /**
     *
     * @param lb
     * @param key
     * @return
     */
    @Override
    public Server choose(ILoadBalancer lb, Object key) {
        //可以在filter构造自己的bean 比如传入带前端版本号的bean进行灰度负载
       String version= MyLoadBalancerClientFilter.serverWebExchangeHolder.get().getRequest().getHeaders().getFirst("version");
       if(StringUtils.isEmpty(version)){
           version= MyLoadBalancerClientFilter.serverWebExchangeHolder.get().getRequest().getQueryParams().getFirst("version");
       }
       //如果 env=dev 则跳转到非服务器ip上 用于开发前后端调试
        String env= MyLoadBalancerClientFilter.serverWebExchangeHolder.get().getRequest().getHeaders().getFirst("env");
        if(StringUtils.isEmpty(env)){
            env= MyLoadBalancerClientFilter.serverWebExchangeHolder.get().getRequest().getQueryParams().getFirst("env");
        }
        if("dev".equals(env)){
            //获取配置文件的服务器ip 进行排除
            String serverIp="192.168.20.157";
            List<Server> reachableServers = lb.getReachableServers();
            Server devServer = null;
            for(int i=0;i<reachableServers.size();i++){
                Server server = reachableServers.get(i);
                if(server.isAlive()&&!server.getHost().contains(serverIp)&& server.isReadyToServe()){
                    //TODO 缓存起来
                    devServer = server;
                    break;
                }
            }
            return devServer;
        }
        return super.choose(lb, key);
    }
}
