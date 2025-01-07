package com.med.batch.mqrabbit.core;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.DefaultJackson2JavaTypeMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RabbitMQConfig {
    @Value("${mq.queue-customer}")
    private String customerQueue;
    @Value("${mq.queue-customer-bnk}")
    private String customerQueueBnk;
    @Value("${mq.exchange-customer}")
    private String customerTopic;
    @Value("${mq.exchange-customer-bnk}")
    private String customerTopicBnk;
    @Value("${mq.binding-customer}")
    private String customerBindingKey;
    @Value("${mq.binding-customer-bnk}")
    private String customerBindingKeyBnk;

    @Bean
    public Queue customerQueue() {
        return new Queue(customerQueue, true);
    }

    @Bean
    public Queue customerbnkQueue() {
        return new Queue(customerQueueBnk, true);
    }

    @Bean
    public Exchange customerExchange() {
        return new DirectExchange(customerTopic, true, false);
    }

    @Bean
    public Exchange customerBnkExchange() {
        return new DirectExchange(customerTopicBnk, true, false);
    }

    @Bean
    public Binding customerBinding(Queue customerQueue, Exchange customerExchange) {
        return BindingBuilder.bind(customerQueue).to(customerExchange).with(customerBindingKey).noargs();
    }

    @Bean
    public Binding customerBnkBinding(Queue customerbnkQueue, Exchange customerBnkExchange) {
        return BindingBuilder.bind(customerbnkQueue).to(customerBnkExchange).with(customerBindingKeyBnk).noargs();
    }

    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        Jackson2JsonMessageConverter converter = new Jackson2JsonMessageConverter();
        DefaultJackson2JavaTypeMapper typeMapper = new DefaultJackson2JavaTypeMapper();

        Map<String, Class<?>> idClassMapping = new HashMap<>();
        idClassMapping.put("com.med.back_mq.model.Customer", com.med.batch.model.Customer.class);

        typeMapper.setIdClassMapping(idClassMapping);
        converter.setJavaTypeMapper(typeMapper);
        return converter;
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory,
                                         Jackson2JsonMessageConverter messageConverter) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter);
        rabbitTemplate.setExchange(customerTopic);
        rabbitTemplate.setDefaultReceiveQueue(customerQueue);
        rabbitTemplate.setRoutingKey(customerBindingKey);
        rabbitTemplate.containerAckMode(AcknowledgeMode.MANUAL);
        return rabbitTemplate;
    }
}
