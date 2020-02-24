#### RabbitMQ可能存在的数据丢失问题
- 生产者写消息的过程中，消息没到RabbitMq在网络传输过程中就丢了；或者是消息到了RabbitMQ，但是内部出错了没保存下来
- RabbitMq接收到了消息之后先暂存在自己的内存里， 结果消费者还没来得及消费，RabbitMq就挂掉了，就导致暂存在内存里的数据丢失。
- 消费者消费到了消息，但是还没来得及处理，自己就挂掉了，但是RabbitMQ以为这个消费者已经处理完了。
##### 生产者弄丢数据
- 可以选择用RabbitMq提供的==事务功能（同步）==，就是生产者发送数据之前开启RabbitMq事务（<font color="red">channel.txSelect</font>）,然后发送消息，如果消息没有成功被RabbitMq接收到，那么生产者会收到异常报错，此时就可以回滚事务（<font color="red">channel.txRollback</font>），然后重试发送消息；如果收到了消息，那么可以提交事务（<font color="red">channel.txCommit</font>）。但是问题为事务机制是同步的，会导致生产者发送消息的吞吐量降下来。
- 可以开启==confirm模式(异步)==。先把channel设置成confirm模式<font color="red">channel.confirm</font>，发送一个消息，发送完消息之后，RabbitMq如果接收到了这条消息，就会回调你的生产者本地的一个接口，通知你这条消息已经收到。如果未接受，也会回调你的接口，告诉你接收失败。
    - 注：你要在生产者中提供一个供回调的接口的实现
        ```
                    public void ack(String messageId){
                        
                    }
                    public void nack(String messageId){
                        
                    }
        ```
##### RabbitMq弄丢数据
- 开启RabbitMq持久化，消息写入之后会持久化到磁盘，即使RabbitMq挂了，恢复之后会自动读取之前存储的数据，一般数据不会丢。（RabbitMq还没持久化，自己就挂了，可能导致少量数据会丢失，但是概率极小）
- 设置持久化有两个步骤，第一个是创建queue的时候将其设置为持久化的，这样就可以保证RabbitMq持久化queue的元数据，但是不会持久化queue里的数据；第二个时发送消息的时候将消息的deliveryMode设置为2，就是将消息设置为持久化的，此时RabbitMq就会将消息持久化到磁盘中去。必须要同时设置这两个持久化才行，Rabbit即使挂掉，重启后也会从磁盘上恢复queue。
- 持久化可以和生产者的confirm机制配合使用，只有消息被持久化到磁盘之后，才会通知生产者ack，所以即使持久化到磁盘之前RabbitMq挂了，数据丢了，生产者收不到ack，也可以重发。
##### 消费者弄失数据
- 原因：打开了消费者的autoAck的机制。
- autoAck机制：如果消费者消费一条消息，还在处理中，还没处理完，此时消费者自动autoAck了，通知RabbitMq说这条消息已经被消费了后消费者系统宕机了，消息丢失，且RabbitMq以为消息已经被处理掉。 
- 需要将autoAck关闭，自己确定处理完之后，再发送ack给RabbitMq，如果没处理完就宕机，此时RabbitMq没收到你发的ack消息，RabbitMq就会将这条消息重新分配给其他的消费者去处理。
---
#### 生产者 ---confirm机制---> RabbitMq（持久化） ---关闭autoAck---> 消费者