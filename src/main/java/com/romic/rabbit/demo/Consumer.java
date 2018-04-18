package com.romic.rabbit.demo;
import com.rabbitmq.client.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;
public class Consumer {

    private static final Logger logger = LoggerFactory.getLogger(Consumer.class);

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUsername("guest");
        factory.setPassword("guest");
        factory.setHost("10.47.163.42");
        //建立到代理服务器到连接
        Connection conn = factory.newConnection();
        //获得信道
        final Channel channel = conn.createChannel();
        //声明交换器
        channel.exchangeDeclare(Producer.EXCHANGE_NAME, "topic");
        //声明队列
//        String queueName = channel.queueDeclare("test-queue",false,false,false,null).getQueue();

        String queueName = "agh-user";
        String routingKey = "*.info";
        //绑定队列，通过键 hola 将队列和交换器绑定起来
        channel.queueBind(queueName, Producer.EXCHANGE_NAME, routingKey);

        while(true) {
            //消费消息
            boolean autoAck = false;
            String consumerTag = "";
            channel.basicConsume(queueName, autoAck, consumerTag, new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag,
                                           Envelope envelope,
                                           AMQP.BasicProperties properties,
                                           byte[] body) throws IOException {
                    String routingKey = envelope.getRoutingKey();
                    String contentType = properties.getContentType();
//                    System.out.println("消费的路由键：" + routingKey);
//                    System.out.println("消费的内容类型：" + contentType);
                    long deliveryTag = envelope.getDeliveryTag();
                    //确认消息
                    channel.basicAck(deliveryTag, false);
//                    System.out.println("消费的消息体内容：");
                    String bodyStr = new String(body, "UTF-8");
//                    System.out.println(bodyStr);


                    logger.info("获取消息 [{}] {}", routingKey, bodyStr);

                }
            });
        }
    }
}