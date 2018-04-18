package com.romic.rabbit.listener;
  
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ListenerCorp {

    private static final Logger logger = LoggerFactory.getLogger(ListenerCorp.class);

    @RabbitListener(queues = "agh-user-romic")
    @RabbitHandler
    public void process(byte[] strings) {
        String string = new String(strings);
        logger.info(string);
    }

}  