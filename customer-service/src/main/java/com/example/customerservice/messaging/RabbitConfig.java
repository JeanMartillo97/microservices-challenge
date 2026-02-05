package com.example.customerservice.messaging;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    public static final String EXCHANGE = "banking.customer.exchange";
    public static final String QUEUE = "banking.customer.queue";
    public static final String RK_CREATED = "customer.created";
    public static final String RK_UPDATED = "customer.updated";
    public static final String RK_DELETED = "customer.deleted";

    @Bean
    public TopicExchange customerExchange() {
        return new TopicExchange(EXCHANGE, true, false);
    }

    @Bean
    public Queue customerQueue() {
        return QueueBuilder.durable(QUEUE).build();
    }

    @Bean
    public Binding bindCreated(Queue customerQueue, TopicExchange customerExchange) {
        return BindingBuilder.bind(customerQueue).to(customerExchange).with(RK_CREATED);
    }

    @Bean
    public Binding bindUpdated(Queue customerQueue, TopicExchange customerExchange) {
        return BindingBuilder.bind(customerQueue).to(customerExchange).with(RK_UPDATED);
    }

    @Bean
    public Binding bindDeleted(Queue customerQueue, TopicExchange customerExchange) {
        return BindingBuilder.bind(customerQueue).to(customerExchange).with(RK_DELETED);
    }

    @Bean
    public MessageConverter jacksonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory cf, MessageConverter converter) {
        RabbitTemplate rt = new RabbitTemplate(cf);
        rt.setMessageConverter(converter);
        return rt;
    }
}
