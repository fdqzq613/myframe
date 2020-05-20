package com.some.mq.controller;

import com.some.mq.producer.SendService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "mqtest相关操作", tags = "mqtest相关操作")
@RestController
@RequestMapping("/mq")
@Slf4j
public class MqTestController {
    @Autowired
    private SendService sendService;
    @Autowired
    private AmqpTemplate amqpt;

    @ApiOperation(value = "send", notes = "send", httpMethod = "POST")
    @RequestMapping(value = "/send", method = {RequestMethod.POST})
    public void send(String msg) {
        sendService.send(msg);
    }

    @ApiOperation(value = "send2", notes = "send2", httpMethod = "POST")
    @RequestMapping(value = "/send2", method = {RequestMethod.POST})
    public void send2(String msg) {
        sendService.send2(msg);
    }

	@ApiOperation(value = "send3", notes = "send3", httpMethod = "POST")
	@RequestMapping(value = "/send3", method = {RequestMethod.POST})
	public void send3(String msg) {
		//第一个参数是交换机，第二个参数是routingKey,第三个参数是要发送的消息,支持实体对象
		amqpt.convertAndSend("some.mq","some2-key", msg);
	}




}
