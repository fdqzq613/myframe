package com.some.mq.producer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

/**
 * 发送消息
 * @version V1.0
 * @author qzq
 * @date   2020年4月13日
 */
@Service
@Slf4j
public class SendService {
	@Autowired
	private StreamOutputChannel streamOutputChannel;
	public void send(String msg){
		Message<String> message = MessageBuilder
				//header 作为routingKey
				.withPayload(msg).setHeader("name","some-key")
				.build();
		streamOutputChannel.send().send(message );
	}

	public void send2(String msg){
		Message<String> message = MessageBuilder
				//header 作为routingKey
				.withPayload(msg).setHeader("name","some2-key")
				.build();
		streamOutputChannel.send().send(message );
	}
}
