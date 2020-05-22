package com.some.order.controller;

import com.some.common.result.RespResult;
import com.some.common.utils.IdUtils;
import com.some.order.mq.vo.KcOrderVo;
import com.some.order.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description: order
 * @vsersion: V1.0
 * @author: qzq
 * @date: 2020-05-20 13:27
 */
@Api(value = "订单相关操作", tags = "订单相关操作")
@RestController
@RequestMapping("/mq")
@Slf4j
public class OrderController {
    @Autowired
    private OrderService orderService;

    @ApiOperation(value = "获取订单号", notes = "获取订单号", httpMethod = "GET")
    @RequestMapping(value = "/getOrderNo", method = RequestMethod.GET)
    public RespResult<Long> getOrderNo(){
        return RespResult.create(orderService.getOrderNo());
    }

    /**
     * 下单
     * @param kcOrderVo
     * @return
     */
    @ApiOperation(value = "下单", notes = "下单", httpMethod = "POST")
    @RequestMapping(value = "/order", method = RequestMethod.POST)
    public RespResult<String> order(KcOrderVo kcOrderVo){

        log.info("前台用户：开始下单，NO：{}",kcOrderVo);
        //发送
        return orderService.preOrder(kcOrderVo);
    }

    @ApiOperation(value = "test下单", notes = "test下单", httpMethod = "POST")
    @RequestMapping(value = "/testOrder", method = {RequestMethod.POST,RequestMethod.GET})
    public RespResult<String> testOrder(){

        long orderNo = orderService.getOrderNo();
        log.info("获取订单号:{}",orderNo);
        KcOrderVo kcOrderVo = new KcOrderVo();
        kcOrderVo.setNum(2);
        kcOrderVo.setOrderNo(orderNo);
        kcOrderVo.setGoodsNo(1);
        log.info("前台用户：开始下单，NO：{}",kcOrderVo);
        RespResult rs = orderService.preOrder(kcOrderVo);
        log.info(rs.toString());
        return rs;
    }


    /**
     * 支付
     * @param orderNo
     * @return
     */
    @ApiOperation(value = "支付成功回调", notes = "支付成功回调", httpMethod = "POST")
    @RequestMapping(value = "/payCallback", method = RequestMethod.POST)
    public RespResult<String> payCallback(long orderNo){
        RespResult rs = orderService.payCallback(orderNo);
        log.info(rs.toString());
        return rs;
    }
}
