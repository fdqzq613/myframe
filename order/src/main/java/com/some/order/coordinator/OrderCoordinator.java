package com.some.order.coordinator;

import com.some.order.cancel.KcCancel;
import com.some.order.cancel.OrderCancel;
import com.some.order.comfirm.KcComfirm;
import com.some.order.comfirm.OrderComfirm;
import com.some.order.handler.KcHandler;
import com.some.order.handler.OrderHandler;
import com.some.order.mq.vo.KcOrderVo;
import com.some.web.utils.ApplicationContextUtils;
import lombok.Data;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @description: 订单事务协调者-tcc
 * @vsersion: V1.0
 * @author: qzq
 * @date: 2020-05-22 14:21
 */
@Slf4j
public class OrderCoordinator {
    //剩余未完成任务
    private  Map<String,TccWorker> works = new ConcurrentHashMap<>();
    @Getter
    private long startTime;
    private Object lock = new Object();
    @Getter
    private KcOrderVo kcOrderVo;
    private int transactionNum;
    //执行次数
    @Getter
    private int execTimes;
    @PostConstruct
    public void init(){

        log.info("create");
    }
    public void orderTry(KcOrderVo kcOrderVo){
        TccWorker orderWorker = new TccWorker("order",null,new OrderComfirm(),new OrderCancel(),ApplicationContextUtils.getBean(OrderHandler.class));
        TccWorker kcWorker = new TccWorker("kc",null,new KcComfirm(),new KcCancel(),ApplicationContextUtils.getBean(KcHandler.class));
        works.put(orderWorker.getName(),orderWorker);
        works.put(kcWorker.getName(),kcWorker);
        this.kcOrderVo = kcOrderVo;
        startTime = System.currentTimeMillis();
        //启动事务，需要完成事务数
        transactionNum = works.size();
        log.info("开启订单事务");
    }
    public void cancel(String notCancelNo){
        log.info("订单失败回滚:{}",this.getKcOrderVo().getOrderNo());
        for(TccWorker tccWorker:works.values()){
              if(tccWorker.getTccCancel().getNo().equals(notCancelNo)){
                  continue;
              }
            tccWorker.getTccCancel().cancel(this.getKcOrderVo());
        }
    }

    public boolean tryNext(){
        if(this.execTimes>3){
            //TODO 超过次数
            return false;
        }
        doHandle(this.getKcOrderVo());
        return true;
    }
    public void doHandle(KcOrderVo kcOrderVo){
        execTimes++;
        works.values().forEach(tccWorker->{
            tccWorker.getHandler().doHandle(kcOrderVo,this);
        });
    }
    public  void checkFinish(){
        try {
            synchronized (lock){
                lock.wait();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public synchronized void comfirm(String no){
        TccWorker tccWorker = works.get(no);
        if(tccWorker==null){
            log.warn("已经确认完:{}",no);
            return;
        }
        tccWorker.getTccComfirm().comfirm();
        transactionNum--;
        works.remove(no);
        if(transactionNum==0){
            //全部都确认了  提交
            log.info("全部确认完成,订单事务完成");
            synchronized (lock){
                log.info("通知订单完成");
                lock.notifyAll();
                OrderCoordinatorHolder.remove(this.getKcOrderVo().getOrderNo());
            }
        }
    }
    @Data
    class TccWorker{

        private  ITccTry tccTry;
        private  ITccComfirm tccComfirm;
        private  IHandler handler;

        private  ITccCancel tccCancel;
        private String name;
        public TccWorker(String name,ITccTry tccTry,ITccComfirm tccComfirm,ITccCancel tccCancel,IHandler handler){
            this.name=name;
            this.tccTry = tccTry;
            this.tccComfirm = tccComfirm;
            this.tccCancel = tccCancel;
            this.handler = handler;
        }
    }
}
