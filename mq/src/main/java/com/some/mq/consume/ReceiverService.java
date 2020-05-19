package com.some.mq.consume;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * 接收消息
 * @version V1.0
 * @author qzq
 * @date   2020年4月13日
 */
@Service
@Slf4j
public class ReceiverService {
	@StreamListener(StreamInputChannel.INPUT)
	public void onReceiver(String msg,@Header(AmqpHeaders.CHANNEL) Channel channel,
						   @Header(AmqpHeaders.DELIVERY_TAG) Long deliveryTag){
		log.info("1接收到消息:{}",msg);
		//
		try {
			channel.basicAck(deliveryTag, false);//手动确认
		} catch (IOException e) {
			log.error("手动确认失败",e);
		}
	}

	@StreamListener(StreamInputChannel.INPUT2)
	public void onReceiver2(String msg,@Header(AmqpHeaders.CHANNEL) Channel channel,
						   @Header(AmqpHeaders.DELIVERY_TAG) Long deliveryTag){
		log.info("2接收到消息:{}",msg);
		//
		try {
			channel.basicAck(deliveryTag, false);//手动确认
		} catch (IOException e) {
			log.error("手动确认失败",e);
		}
	}




}
