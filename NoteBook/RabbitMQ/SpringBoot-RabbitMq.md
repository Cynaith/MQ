### SpringBoot整合RabbitMQ
---
### Fanout订阅模式
#### 导入依赖
```xml
    <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-amqp</artifactId>
    </dependency>
```
####  配置文件
```
spring:
  rabbitmq:
    port: 15672
    username: guest
    password: guest
    host: localhost

# 定义交换机
order.fanout.exchange: order.fanout
# log队列
order.fanout.log.queue: order.fanout.log.queue
# mail队列
order.fanout.mail.queue: order.fanout.mail.queue
```

#### 消费者

```
    @RabbitListener(bindings = @QueueBinding(value =
        @Queue(value = "{order.fanout.log.queue}", autoDelete = "true"),
        exchange = @Exchange(value = "${order.fanout.exchange}",
        type = ExchangeTypes.FANOUT),
         key = ""))
    public class Customer{
        @RabbitHandler
        public void sendMessage(String message) {
            System.out.println("log------------------->"+message);
        }
    }
```
- 会把申请的队列和交换机进行绑定
- 确定消息模式 fanout direct topic
- 确定队列queue的持久性 autoDelete
#### 生产者
```
//调用时可Autowired
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
```
---
### Topic主题匹配模式
```
@RabbitListener(bindings = @QueueBinding(value =
        @Queue(value = "{order.fanout.email.queue}", autoDelete = "true"),
        exchange = @Exchange(value = "${order.fanout.exchange}",type = ExchangeTypes.TOPIC),
        key = "*.email.*"
        //通配符
```
---