package com.some.order.mq.consume;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

/**
 * 
 * @version V1.0
 * @author qzq
 * @date   2020年4月13日
 */
public interface KcFinishInputChannel {
	public static final String INPUT = "kc-finish-input";
	@Input(INPUT)
	SubscribableChannel input();
}
