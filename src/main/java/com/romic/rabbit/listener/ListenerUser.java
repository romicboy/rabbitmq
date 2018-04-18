package com.romic.rabbit.listener;
  
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ListenerUser {

    private static final Logger logger = LoggerFactory.getLogger(ListenerUser.class);


    @RabbitListener(queues = "agh-user")
    @RabbitHandler
    public void process(byte[] strings) {
        String string = new String(strings);
        logger.info(string);
    }

}  