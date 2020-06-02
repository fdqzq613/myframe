package com.some.jdbc.service;

import com.some.common.utils.IdUtils;
import com.some.jdbc.domain.Order;
import com.some.jdbc.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;


@Service
public class OrderService {
    @Autowired
    private OrderMapper orderMapper;
    private Random r = new Random();
    public int save(Order order){
        long id = IdUtils.getId();
        //测试用 实际不会重复

        long orderNo =  Long.valueOf(r.nextInt(2));
        order.setId(id);
        order.setOrderNo(orderNo);
        //模拟用户
        order.setCreateUserid(  Long.valueOf(r.nextInt(2)));

        order.setCreateTime(LocalDateTime.now());
        return orderMapper.insert(order);
    }
    public List<Order> list(){
        return orderMapper.selectList(null);
    }


}
