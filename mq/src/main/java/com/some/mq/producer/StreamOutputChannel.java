package com.some.mq.producer;


import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.SubscribableChannel;

/**
 * 
 * @version V1.0
 * @author qzq
 * @date   2020年4月13日
 */
public interface StreamOutputChannel {
	public static final String OUTPUT = "some-output";

	@Output(OUTPUT)
	SubscribableChannel send();

	

}
