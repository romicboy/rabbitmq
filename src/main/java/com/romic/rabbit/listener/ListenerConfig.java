package com.romic.rabbit.listener;
  
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
public class ListenerConfig {
  
    private static final Logger logger = LoggerFactory.getLogger(ListenerConfig.class);

    private final static String EXCHANGE_NAME = "topic-exchange-romic";

//    @Bean
//    public Jackson2JsonMessageConverter customConverter() {
//        Jackson2JsonMessageConverter converter = new Jackson2JsonMessageConverter();
//        converter.setClassMapper(new ClassMapper() {
//            @Override
//            public Class<?> toClass(MessageProperties properties) {
//                properties.setHeader("__TypeId__", User.class.getName());
//                return User.class;
//            }
//            @Override
//            public void fromClass(Class<?> clazz, MessageProperties properties) {
//
//            }
//        });
//        return converter;
//    }

    @Bean
    TopicExchange exchange() {
        return new TopicExchange(EXCHANGE_NAME);
    }

    @Bean
    public Queue queueMessage() {
        return new Queue("agh-user-romic");
    }

    @Bean
    public Queue queueMessages() {
        return new Queue("agh-user");
    }

    @Bean
    Binding bindingExchangeMessage(Queue queueMessage, TopicExchange exchange) {
        return BindingBuilder.bind(queueMessage).to(exchange).with("corp.info");
    }

    @Bean
    Binding bindingExchangeMessages(Queue queueMessages, TopicExchange exchange) {
        return BindingBuilder.bind(queueMessages).to(exchange).with("user.info");
    }

}  