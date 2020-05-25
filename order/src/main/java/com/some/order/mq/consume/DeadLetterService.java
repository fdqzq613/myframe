package com.some.order.mq.consume;

import com.rabbitmq.client.Channel;
import com.some.order.mq.vo.KcOrderVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @description:
 * @vsersion: V1.0
 * @author: qzq
 * @date: 2020-05-25 09:51
 */
@Slf4j
@Service
public class DeadLetterService {
    @StreamListener(DeadLetterInputChannel.INPUT)
    public void onReceiver(KcOrderVo kcOrderVo, @Header(AmqpHeaders.CHANNEL) Channel channel,
                           @Header(AmqpHeaders.DELIVERY_TAG) Long deliveryTag){
        log.info("接收到死信消息:{}",kcOrderVo);

       try{

           //TODO 死信消息处理 重试一次
        } catch (Exception e) {
            log.error("手动确认失败",e);
        }finally {
            try {
                channel.basicAck(deliveryTag, false);//手动确认
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}
