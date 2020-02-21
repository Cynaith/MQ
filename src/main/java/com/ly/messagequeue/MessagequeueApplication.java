package com.ly.messagequeue;

import com.ly.messagequeue.SpringBootRabbitMQ.SpringBootProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MessagequeueApplication {



    public static void main(String[] args) {
        SpringApplication.run(MessagequeueApplication.class, args);
    }



}
