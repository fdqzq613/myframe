package com.some.order.mq.consume;

import com.rabbitmq.client.Channel;
import com.some.order.mq.vo.KcFinishVo;
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
public class KcService {
	@StreamListener(KcFinishInputChannel.INPUT)
	public void onReceiver(KcFinishVo kcFinishVo, @Header(AmqpHeaders.CHANNEL) Channel channel,
						   @Header(AmqpHeaders.DELIVERY_TAG) Long deliveryTag){
		log.info("接收到库存扣除消息:{}",kcFinishVo);

		//设置订单状态
		String orderSql = "";
		try {
			channel.basicAck(deliveryTag, false);//手动确认
		} catch (IOException e) {
			log.error("手动确认失败",e);
		}
		log.info("订单完成:{}",kcFinishVo);
	}







}
