package com.ly.messagequeue.SpringBootRabbitMQ;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.UUID;

@Component
public class SpringBootProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    //Amqp是一个接口，最终找到的实现类是RabbitTemplate
//    @Autowired
//    private AmqpTemplate rabbitTemplate;


    //定义交换机
    @Value("${order.fanout.exchange}")
    private String exchangeName;

    //路由key
    private String routeKey = "";

    public void sendMessage(int i, Channel channel) throws IOException {
        String orderId = UUID.randomUUID().toString();
        String message = i + "订单信息为：" + orderId;
        System.out.println("test:" + message);
        /**
         * 这个qos,处理并发消息堆积的时候，如果queue堆积很大的消息量，这个时候如果你直接启动消费者，
         * 可能会直接把消费者冲垮，造成消息丢失。
         * false: 代表不批量处理
         * 1: 表示一条一条接收
         */
//        channel.basicQos(1, false);
        rabbitTemplate.convertAndSend(exchangeName, routeKey, message);
    }


}
