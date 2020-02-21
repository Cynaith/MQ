package com.ly.messagequeue.Simple;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class SimpleProducer {
    public static final int PORT = 15672;

    public static final String HOST = "localhost";

    public static final String VIRTUTAL_HOST = "/";

    public static final String USERNAME = "guest";

    public static final String PASSWORD = "guest";

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
//        connectionFactory.setUsername(USERNAME);
//        connectionFactory.setPassword(PASSWORD);
//        connectionFactory.setVirtualHost(VIRTUTAL_HOST);
//        connectionFactory.setPort(PORT);
        connectionFactory.setHost(HOST);

        Connection connection = connectionFactory.newConnection();

        Channel channel = connection.createChannel();

        for (int i = 0; i < 100; i++) {
            String msg = "Hello RabbitMQ!" + i;
            /**
             * param 1 :交换机的名字
             * param 2 :路由key或者队列
             * param 3 :附属参数
             * param 4 :消息内容
             * 没有交换机的发送，这个时候test001就不是路由key，而是queue
             */
            channel.basicPublish("", "test001", null, msg.getBytes());
        }
        channel.close();
        connection.close();


    }


}
