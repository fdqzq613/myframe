package com.some.jdbc.controller;

import com.some.common.result.RespResult;
import com.some.jdbc.domain.Order;
import com.some.jdbc.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @description:
 * @vsersion: V1.0
 * @author: qzq
 * @date: 2020-06-02 13:31
 */
@Api(value = "订单相关操作", tags = "订单相关操作")
@RestController
@RequestMapping("/order")
@Slf4j
public class OrderController {
    @Autowired
    private OrderService orderService;

    @ApiOperation(value = "下单", notes = "下单", httpMethod = "POST")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public RespResult<Integer> save(Order order){
         return RespResult.create( orderService.save(order));
    }

    @ApiOperation(value = "list", notes = "list", httpMethod = "GET")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public RespResult<List<Order>> list(){
        return RespResult.create( orderService.list());
    }
}
