package com.some.order.mq.producer;


import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.SubscribableChannel;

/**
 * 
 * @version V1.0
 * @author qzq
 * @date   2020年4月13日
 */
public interface KcOutputChannel {
	public static final String OUTPUT = "kc-output";

	@Output(OUTPUT)
	SubscribableChannel send();

	

}
