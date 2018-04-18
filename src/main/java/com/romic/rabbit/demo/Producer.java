package com.romic.rabbit.demo;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.TimeoutException;

public class Producer {

    private static final Logger logger = LoggerFactory.getLogger(Producer.class);

    static final String EXCHANGE_NAME = "topic-exchange-romic";

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {

        ConnectionFactory factory = new ConnectionFactory();
// "guest"/"guest" by default, limited to localhost connections
        factory.setUsername("guest");
        factory.setPassword("guest");
        factory.setVirtualHost("/");
        factory.setHost("10.47.163.42");
        factory.setPort(5672);

        Connection conn = factory.newConnection();

        //获得信道
        Channel channel = conn.createChannel();
        //声明交换器
//        channel.exchangeDeclare(EXCHANGE_NAME, "direct",true);

        channel.exchangeDeclare(EXCHANGE_NAME,"topic",true);

//        String routingKey = "hola";
        String[] routingKeys = new String[] { "kernal.info", "auth.info" };

        while (true){
            for (String routingKey : routingKeys) {
                //发布消息
                String messageBody = UUID.randomUUID().toString();
                channel.basicPublish(EXCHANGE_NAME, routingKey, null, messageBody.getBytes());
                logger.info("发送消息 [{}] {}", routingKey, messageBody);
            }
            Thread.sleep(100);
        }

//        channel.close();
//        conn.close();
    }

    @Resource
    private RabbitTemplate rabbitTemplate;

}
