//package com.ly.messagequeue.Simple;
//
//import com.rabbitmq.client.Channel;
//import com.rabbitmq.client.Connection;
//import com.rabbitmq.client.ConnectionFactory;
//import com.rabbitmq.client.QueueingConsumer;
//
//import java.io.IOException;
//import java.util.concurrent.TimeoutException;
//
//public class SimpleCostomer {
//
//    public static final int PORT = 15672;
//
//    public static final String HOST = "localhost";
//
//    public static final String VIRTUTAL_HOST = "/";
//
//    public static final String USERNAME = "guest";
//
//    public static final String PASSWORD = "guest";
//
//    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
//        ConnectionFactory connectionFactory = new ConnectionFactory();
////        connectionFactory.setUsername(USERNAME);
////        connectionFactory.setPassword(PASSWORD);
////        connectionFactory.setVirtualHost(VIRTUTAL_HOST);
////        connectionFactory.setPort(PORT);
//        connectionFactory.setHost(HOST);
//
//        Connection connection = connectionFactory.newConnection();
//
//        Channel channel = connection.createChannel();
//
//        String queueName = "test001";
//
//        /**
//         *  param 1:队列的名称
//         *  param 2:设置是否持久化，为true 则设置队列为持久化。持久化的队列会存盘，在服务器重启的时候可以保证不丢失相关信息。
//         *  param 3:autoDelete 是否自动删除队列，当最后一个消费者断开连接之后队列是否被自动删除。
//         *  param 4: exclusive 是否排外。
//         *  param 5: arguments 设置队列的其他一下参数
//         */
//        channel.queueDeclare(queueName, false, true, false, null);
//
//        QueueingConsumer queueingConsumer = new QueueingConsumer(channel);
//
//        /**
//         * param 2: autoAck 是否自动确认消息，true自动确认，false不自动要手动调用，建议设置为false
//         */
//        channel.basicConsume(queueName, true, queueingConsumer);
//
//        while (true){
//            QueueingConsumer.Delivery delivery = queueingConsumer.nextDelivery();
//            String msg = new String(delivery.getBody());
//            System.err.println("1---消费端:"+msg);
//        }
//
//
//
//    }
//}
