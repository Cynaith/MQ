package com.ly.messagequeue.Direct;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * 消息生成者
 */public class DriectProducer {

        public final static String QUEUE_NAME="direct_queue";

        public static void main(String[] args) throws IOException, TimeoutException {
            DirectUtils.checkServer();

            //创建连接工厂
            ConnectionFactory factory = new ConnectionFactory();
            //设置RabbitMQ相关信息
            factory.setHost("localhost");
            //创建一个新的连接
            Connection connection = factory.newConnection();
            //创建一个通道
            Channel channel = connection.createChannel();

            for (int i = 0; i < 100; i++) {
                String message = "direct 消息 " +i;
                //发送消息到队列中
                channel.basicPublish("", QUEUE_NAME, null, message.getBytes("UTF-8"));
                System.out.println("发送消息： " + message);

            }
            //关闭通道和连接
            channel.close();
            connection.close();
        }

}
