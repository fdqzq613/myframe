package com.some.order.coordinator;

import com.some.order.cancel.KcCancel;
import com.some.order.cancel.OrderCancel;
import com.some.order.comfirm.KcComfirm;
import com.some.order.comfirm.OrderComfirm;
import com.some.order.handler.KcHandler;
import com.some.order.handler.OrderHandler;
import com.some.order.mq.vo.KcOrderVo;
import com.some.web.utils.ApplicationContextUtils;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description: 订单事务协调者-tcc
 * @vsersion: V1.0
 * @author: qzq
 * @date: 2020-05-22 14:21
 */
@Slf4j
public class OrderCoordinator {
    private  Map<String,ITccTry> tryMap = new HashMap<>();
    private  Map<String,ITccComfirm> comfirmMap = new HashMap<>();
    private  List<IHandler> handlers = new ArrayList<>();

    private  Map<String,ITccCancel> cancelMap = new HashMap<>();
    private Object lock = new Object();
    @Getter
    private KcOrderVo kcOrderVo;
    private int transactionNum;
    @PostConstruct
    public void init(){

        log.info("create");
    }
    public void orderTry(KcOrderVo kcOrderVo){
        this.kcOrderVo = kcOrderVo;
        handlers.add(ApplicationContextUtils.getBean(KcHandler.class));
        handlers.add(ApplicationContextUtils.getBean(OrderHandler.class));
        comfirmMap.put("order",new OrderComfirm());
        comfirmMap.put("kc",new KcComfirm());
        cancelMap.put("order",new OrderCancel());
        cancelMap.put("kc",new KcCancel());
        //启动事务，需要完成事务数
        transactionNum = comfirmMap.size();
        log.info("开启订单事务");
    }
    public void cancel(String notCancelNo){
        log.info("订单失败回滚:{}",this.getKcOrderVo().getOrderNo());
        for(ITccCancel iTccCancel:cancelMap.values()){
              if(iTccCancel.getNo().equals(notCancelNo)){
                  continue;
              }
            iTccCancel.cancel(this.getKcOrderVo());
        }
    }

    public void doHandle(KcOrderVo kcOrderVo){
        handlers.forEach(handler->{
            handler.doHandle(kcOrderVo,this);
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
        ITccComfirm iTccComfirm = comfirmMap.get(no);
        if(iTccComfirm==null){
            log.warn("已经确认完:{}",no);
            return;
        }
        iTccComfirm.comfirm();
        transactionNum--;
        comfirmMap.remove(no);
        if(transactionNum==0){
            //全部都确认了  提交
            log.info("全部确认完成,订单事务完成");
            synchronized (lock){
                log.info("通知订单完成");
                lock.notifyAll();
            }
        }
    }
}
