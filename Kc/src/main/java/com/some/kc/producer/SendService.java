package com.some.kc.producer;

import com.some.kc.vo.KcFinishVo;
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
	private KcFinishOutputChannel kcFinishOutputChannel;

	/**
	 * 预扣库存成功
	 * @param kcFinishVo
	 */
	public void sendPreLock(KcFinishVo kcFinishVo){
		Message<KcFinishVo> message = MessageBuilder
				//header 作为routingKey
				.withPayload(kcFinishVo).setHeader("name","kc-lock-finish-key")
				.build();
		kcFinishOutputChannel.send().send(message );
	}

}
