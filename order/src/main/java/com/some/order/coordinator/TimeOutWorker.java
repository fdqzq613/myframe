package com.some.order.coordinator;

import com.alibaba.druid.support.spring.DruidNativeJdbcExtractor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Map;

/**
 * @description:
 * @vsersion: V1.0
 * @author: qzq
 * @date: 2020-05-22 17:06
 */
@Component
@Slf4j
public class TimeOutWorker {
    private long timeout = 1000*10;
    @Scheduled(cron= "10 * * * * ?") // 每隔10秒扫描
    public void check(){
        log.info("启动定时扫描事务任务");
        Map<Long,OrderCoordinator> holder = OrderCoordinatorHolder.getHolder();
        if(holder.size()==0){
            return;
        }
        holder.values().forEach(orderCoordinator->{
            if (isTimeout(orderCoordinator)) {
                //进行重试
                log.info("订单：{}，超时进行重试",orderCoordinator.getKcOrderVo().getOrderNo());
                boolean can = orderCoordinator.tryNext();
                if(can==false){

                    //超过次数 执行回滚
                    orderCoordinator.cancel("");
                }
            }

        });
    }

    public boolean isTimeout(OrderCoordinator orderCoordinator){
        if(System.currentTimeMillis()-orderCoordinator.getStartTime()>timeout){
           return true;
        }
        return false;
    }
}
