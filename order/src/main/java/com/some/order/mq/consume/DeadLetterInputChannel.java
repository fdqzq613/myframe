package com.some.order.mq.consume;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

/**
 * 
 * @version V1.0
 * @author qzq
 * @date   2020年4月13日
 */
public interface DeadLetterInputChannel {
	public static final String INPUT = "delay-order-input";
	@Input(INPUT)
	SubscribableChannel input();
}
