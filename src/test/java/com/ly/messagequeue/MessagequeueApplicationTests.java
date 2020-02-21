package com.ly.messagequeue;

import com.ly.messagequeue.SpringBootRabbitMQ.SpringBootProducer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MessagequeueApplicationTests {

    @Autowired
    private SpringBootProducer springBootProducer;

    @Test
    void contextLoads() {
        for (int i =0;i<100;i++){
            springBootProducer.sendMessage(i);
        }
    }

}
