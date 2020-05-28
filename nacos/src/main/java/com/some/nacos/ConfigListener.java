package com.some.nacos;

import com.alibaba.cloud.nacos.refresh.NacosContextRefresher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.endpoint.event.RefreshEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * @description:
 * @vsersion: V1.0
 * @author: qzq
 * @date: 2020-05-28 15:39
 */
@Component
@Slf4j
public class ConfigListener {

    @EventListener
    public void init(RefreshEvent refreshEvent)  {
        log.info("刷新事件");
       // NacosContextRefresher nacosContextRefresher = (NacosContextRefresher) refreshEvent.getSource();
    }
}
