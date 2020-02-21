package com.ly.messagequeue.SpringBootRabbitMQ;

import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.stereotype.Component;

@Component
/**
 * 这里做什么
 * 1.会把申请的队列和交换机进行绑定
 * 2.确定消息模式 fanout direct topic
 * 3. 确定队列queue的持久性 autoDelete
 */

@RabbitListener(bindings = @QueueBinding(value =
        @Queue(value = "{order.fanout.email.queue}", autoDelete = "true"),
        exchange = @Exchange(value = "${order.fanout.exchange}",type = ExchangeTypes.FANOUT),
        key = ""
                            ))
public class EmailService {


    @RabbitHandler
    public void sendMessage(String message) {
        System.out.println("email------------------->"+message);
    }
}
