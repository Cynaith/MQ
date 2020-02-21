### RabbitMq

Erlang语言  AMQP协议

---
- 什么情况下使用Rabbitmq
    - **读多写少用缓存 写多读少用队列**
        - **RabbitMq** 跨平台
        - **ActiveMq** java 
        - **Kafka** ZooKeeper
        - **RocketMq** 
    - **解耦:** 系统A在代码中直接调用系统B和C的代码，如果将来D系统接入，系统A还需要修改代码，过于麻烦。
    - **异步:** 将消息写入消息队列，非必要的业务逻辑以异步的方式运行，加快响应速度。
    - **削峰:** 并发量大的时候，所有的请求直接到数据库，造成数据库连接异常。
    - 达到数据的“最终一致”
- AMQP协议(Advanced Message Queuing Protocol 高级消息队列协议)
    - **Server:** 又称Broker，接受客户端的连接，实现AMQP实体服务。安装Rabbitmq-server
    - **Connection:** 连接，应用程序与Broker的网络连接，TCP/IP(安全性 操作系统会对tcp有限制)。
        - Rabbitmq长连接技术，只开一次 -Connection-线程执行
    - **Channel:** 网络信道，几乎所有的操作都会在Channel中进行，Channel是进行消息读写的通道，客户端可以建立对各Channel，每个Channel代表一个会话任务。
    - **Message:** 消息: 服务与应用程序之间传送的数据，由Properties和body组成，Properties可以对消息进行修饰，比如消息的优先级、延迟等高级特性，Body则就是消息的内容。
    - **Virtual Host:** 虚拟地址，用于逻辑隔离，最上层的消息路由，一个虚拟主机路由可以有若干个Exchange和Queue，同一个虚拟主机里面不能有相同名字的Exchange或Queue。
    - **Exchange:** 交换机，接收消息，根据路由间发送消息到绑定的队列。
    - **Bindings:** Exchange和Queue之间的虚拟连接，binding中可以保护多个routing key。
    - **Routing key:** 是一个路由规则，虚拟机可以用它来确定如何路由一个特定消息。
    - **Queue:** 队列:也称为Message Queue，消息队列，保存消息并将他们转发给消费者。