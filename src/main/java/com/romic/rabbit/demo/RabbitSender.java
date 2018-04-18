package com.romic.rabbit.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class RabbitSender {

    private static final Logger logger = LoggerFactory.getLogger(RabbitSender.class);

    @Resource
    private AmqpTemplate rabbitTemplate;

    public void send(String context) {
//        rabbitTemplate.convertAndSend("topic-exchange-romic", "corp.create", context);
        MessageProperties properties = new MessageProperties();

        String exchange = "topic-exchange-romic";
        String routingKey = "corp.info";
        rabbitTemplate.send(exchange, routingKey, new Message(routingKey.getBytes(), properties));
        logger.info("Sender : [{}] [{}] {}", exchange, routingKey, context);

    }

}