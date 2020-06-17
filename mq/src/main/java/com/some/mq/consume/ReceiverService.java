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
			//deliveryTag:该消息的index
			//multiple：是否批量. true 将一次性ack所有小于deliveryTag的消息。
			channel.basicAck(deliveryTag, false);//手动确认

			//deliveryTag:该消息的index。
			//multiple：是否批量. true：将一次性拒绝所有小于deliveryTag的消息。
			//requeue：被拒绝的是否重新入队列。
			//channel.basicNack(deliveryTag, false,true);
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

	@StreamListener(StreamInputChannel.INPUT3)
	public void onReceiver3(String msg,@Header(AmqpHeaders.CHANNEL) Channel channel,
						   @Header(AmqpHeaders.DELIVERY_TAG) Long deliveryTag){
		log.info("1接收到消息:{}",msg);
		//
		try {
			channel.basicAck(deliveryTag, false);//手动确认
		} catch (IOException e) {
			log.error("手动确认失败",e);
		}
	}




}
