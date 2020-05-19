package com.some.mq.controller;

import com.some.mq.producer.SendService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "mqtest相关操作", tags = "mqtest相关操作")
@RestController
@RequestMapping("/mq")
public class MqTestController {
	@Autowired
	private SendService sendService;

	@ApiOperation(value = "send", notes = "send", httpMethod = "POST")
	@RequestMapping(value = "/send", method = {RequestMethod.POST})
    public void send(String msg){
		sendService.send(msg);
    }   


}
