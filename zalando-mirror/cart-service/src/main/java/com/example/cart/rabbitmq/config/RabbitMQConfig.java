package com.example.cart.rabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

public class RabbitMQConfig {

    @Value("${rabbitmq.orderAndPayment.queue.name}")
    private String orderAndPaymentQueue;
    @Value("${rabbitmq.inventory.queue.name}")
    private String inventoryQueue;
    @Value("${rabbitmq.cart.exchange.name}")
    private String exchange;
    @Value("${rabbitmq.orderAndPayment.queue.key}")
    private String orderAndPaymentKey;
    @Value("${rabbitmq.inventory.queue.key}")
    private String inventoryKey;


    @Bean
    public Queue jsonQueue(String queue){
        return new Queue(queue);
    }
    @Bean
    public TopicExchange exchange(String exchange){
        return new TopicExchange(exchange);
    }

    @Bean
    public Binding orderAndPaymentBinding(){
        return BindingBuilder
                .bind(jsonQueue(orderAndPaymentQueue))
                .to(exchange(exchange))
                .with(orderAndPaymentKey);
    }

    @Bean
    public Binding inventoryBinding(){
        return BindingBuilder
                .bind(jsonQueue(inventoryQueue))
                .to(exchange(exchange))
                .with(inventoryKey);
    }

    @Bean
    public MessageConverter converter(){
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(converter());
        return rabbitTemplate;
    }
}
