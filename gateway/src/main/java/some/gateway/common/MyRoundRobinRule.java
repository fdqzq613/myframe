package some.gateway.common;

import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.RoundRobinRule;
import com.netflix.loadbalancer.Server;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

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
        return super.choose(lb, key);
    }
}
