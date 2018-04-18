package com.romic.rabbit.demo;

import com.agh.base.common.json.JacksonObjectMapperFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@Configuration
public class InitConfigurer {

    @Resource
    RabbitTemplate rabbitTemplate;

    @PostConstruct
    public void init(){
        String name = User.class.getName();
        System.out.printf(name);

        Jackson2JsonMessageConverter jackson2JsonMessageConverter = new Jackson2JsonMessageConverter();
        jackson2JsonMessageConverter.setJsonObjectMapper(JacksonObjectMapperFactory.getNotFailureObjectMapper());
        rabbitTemplate.setMessageConverter(jackson2JsonMessageConverter);
    }

}