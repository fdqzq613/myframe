package com.some.mq.controller;

import com.some.mq.consume.StreamInputChannel;
import com.some.mq.producer.SendService;

import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Correlation;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.UUID;

import javax.annotation.PostConstruct;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Api(value = "mqtest相关操作", tags = "mqtest相关操作")
@RestController
@RequestMapping("/mq")
@Slf4j
public class MqTestController {
    @Autowired
    private SendService sendService;
    @Autowired
    private AmqpTemplate amqpt;
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private AmqpAdmin rabbitAdmin;

    @ApiOperation(value = "send", notes = "send", httpMethod = "POST")
    @RequestMapping(value = "/send", method = {RequestMethod.POST})
    public void send(String msg) {
        sendService.send(msg);
    }

    @ApiOperation(value = "send2", notes = "send2", httpMethod = "POST")
    @RequestMapping(value = "/send2", method = {RequestMethod.POST})
    public void send2(String msg) {
        sendService.send2(msg);
    }

    @ApiOperation(value = "send3", notes = "send3", httpMethod = "POST")
    @RequestMapping(value = "/send3", method = {RequestMethod.POST})
    public void send3(String msg) {
        //第一个参数是交换机，第二个参数是routingKey,第三个参数是要发送的消息,支持实体对象
        amqpt.convertAndSend("some.mq", "some2-key", msg);
        //amqpt.convertSendAndReceive("some.mq","some2-key", msg);//可以同步消费者。使用此方法，当确认了所有的消费者都接收成功之后，才触发另一个
    }

    @PostConstruct
    public void initCallback() {
        //消息发送到 Broker触发 也就是只确认是否正确到达 Exchange 中
        rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {

            @Override
            public void confirm(CorrelationData correlationData, boolean ack, String s) {
                if (ack) {
                    System.out.println("消息确认成功");
                } else {
                    //处理丢失的消息（nack）
                    System.out.println("消息确认失败,原因："+s);
                }
            }
        });

        //启动消息失败返回，比如消息有到Broker但是路由不到队列时触发回调  如例子 test-routingKey2
        rabbitTemplate.setReturnCallback(new RabbitTemplate.ReturnCallback() {

            @Override
            public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
                System.out.println("消息主体 message : " + message);
                System.out.println("replyCode : " + replyCode);
                System.out.println("描述：" + replyText);
                System.out.println("消息使用的交换器 exchange : " + exchange);
                System.out.println("消息使用的路由键 routing : " + routingKey);
            }
        });

        rabbitAdmin.declareExchange(new TopicExchange("topic.my.create.exchange",true,false));
        rabbitAdmin.declareQueue(new Queue("my.create.queue",true));
        //绑定队列到交换器，通过路由键
        rabbitAdmin.declareBinding(new Binding("my.create.queue", Binding.DestinationType.QUEUE,
                "topic.my.create.exchange","test-create-routingKey",new HashMap()));
    }



    @ApiOperation(value = "send4", notes = "send4", httpMethod = "POST")
    @RequestMapping(value = "/send4", method = {RequestMethod.POST})
    public void send4(String msg) {

        //第一个参数是交换机，第二个参数是routingKey,第三个参数是要发送的消息,支持实体对象
        //ack
        amqpt.convertAndSend(StreamInputChannel.INPUT3, "test-routingKey", "myTestSome-routingKey:"+msg);
        amqpt.convertAndSend(StreamInputChannel.INPUT3, "test-routingKey2", "myTestSome-routingKey2:"+msg);
        amqpt.convertAndSend(StreamInputChannel.INPUT3, "test-routingKey", "myTestSome-routingKeydd:"+msg,new MessagePostProcessor(){

            @Override
            public Message postProcessMessage(Message message) throws AmqpException {
                return message;
            }

            @Override
            public Message postProcessMessage(Message message, Correlation correlation) {
                return message;
            }
        });
        //不存在路由 nack
        amqpt.convertAndSend("myTestSome-no-Exchange", "some2-key", "myTestSome-no-Exchange:"+msg);
        amqpt.convertAndSend("myTestSome-Exchange", "some-no-key", "some-no-key:"+msg);

        byte[] s = (byte[]) amqpt.receiveAndConvert("delay.order.input.delay-order",1000);
        log.info(new String(s));
    }


}
