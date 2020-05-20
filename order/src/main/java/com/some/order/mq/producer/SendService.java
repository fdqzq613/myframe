package com.some.order.mq.producer;

import com.some.order.mq.vo.KcOrderVo;
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
	private KcOutputChannel kcOutputChannel;
	public void send(KcOrderVo kcOrderVo){
		Message<KcOrderVo> message = MessageBuilder
				//header 作为routingKey
				.withPayload(kcOrderVo).setHeader("name","kc-key")
				.build();
		kcOutputChannel.send().send(message );
	}

}
