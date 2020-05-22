package com.some.order.handler;

import com.some.common.utils.IdUtils;
import com.some.order.coordinator.IHandler;
import com.some.order.coordinator.OrderCoordinator;
import com.some.order.domain.Order;
import com.some.order.domain.OrderDetail;
import com.some.order.mapper.OrderDetailMapper;
import com.some.order.mapper.OrderMapper;
import com.some.order.mq.vo.KcOrderVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.math.BigDecimal;

/**
 * @description:
 * @vsersion: V1.0
 * @author: qzq
 * @date: 2020-05-22 15:00
 */
@Component
@Slf4j
public class OrderHandler implements IHandler {
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private OrderDetailMapper orderDetailMapper;
    @Autowired
    private PlatformTransactionManager transactionManager;
    @Override
    public void doHandle(KcOrderVo kcOrderVo,OrderCoordinator orderCoordinator) {
        //手动事务
        TransactionStatus transactionStatus = null;
        try {
            DefaultTransactionDefinition def = new DefaultTransactionDefinition();
            def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);//新发起一个事务
            transactionStatus = transactionManager.getTransaction(def);
            Order order = new Order();
            order.setOrderNo(kcOrderVo.getOrderNo());
            order.setAmount(BigDecimal.valueOf(2));
            order.setId(IdUtils.getId());
            OrderDetail orderDetail = new OrderDetail();
            BeanUtils.copyProperties(kcOrderVo,orderDetail);
            orderDetail.setId(IdUtils.getId());
            orderMapper.insert(order);
            orderDetailMapper.insert(orderDetail);
            transactionManager.commit(transactionStatus);
            orderCoordinator.comfirm("order");
        } catch (Exception e) {
            log.error("订单生成失败,进行回滚操作", e);
            if (transactionStatus != null) {
                transactionManager.rollback(transactionStatus);

            }
            //其它业务也进行回滚
            orderCoordinator.cancel("order");
            //TODO 插入异常日志
        } finally {
        }
    }
}
