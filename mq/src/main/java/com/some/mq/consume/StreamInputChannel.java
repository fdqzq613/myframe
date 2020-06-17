package com.some.mq.consume;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

/**
 * 
 * @version V1.0
 * @author qzq
 * @date   2020年4月13日
 */
public interface StreamInputChannel {
	public static final String INPUT = "some-input";
	public static final String INPUT2 = "some-input2";
	public static final String INPUT3 = "some-input3";
	@Input(INPUT)
	SubscribableChannel input();

	@Input(INPUT2)
	SubscribableChannel input2();

	@Input(INPUT3)
	SubscribableChannel input3();
}
