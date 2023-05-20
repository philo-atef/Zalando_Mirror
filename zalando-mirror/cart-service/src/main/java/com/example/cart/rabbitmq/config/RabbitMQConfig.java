package com.example.cart.rabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
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
    public Queue inventoryQueue(){
        return new Queue(inventoryQueue);
    }

    @Bean
    public Queue ordersQueue(){
        return new Queue(orderAndPaymentQueue);
    }
    @Bean
    public TopicExchange exchange(){
        return new TopicExchange(exchange);
    }

    @Bean
    public Binding orderAndPaymentBinding(){
        return BindingBuilder
                .bind(ordersQueue())
                .to(exchange())
                .with(orderAndPaymentKey);
    }

    @Bean
    public Binding inventoryBinding(){
        return BindingBuilder
                .bind(inventoryQueue())
                .to(exchange())
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
