package com.ly.messagequeue.SpringBootRabbitMQ;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

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

    public void sendMessage(int i) {
        String orderId = UUID.randomUUID().toString();
        String message = i+"订单信息为：" + orderId;
        System.out.println("test:"+message);

        rabbitTemplate.convertAndSend(exchangeName, routeKey, message);
    }


}
