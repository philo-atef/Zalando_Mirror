package net.example.springboot.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQconfig {
    //spring bean for rabbitmq queue
@Value("${rabbitmq.queque.name}")
private String queue;
@Bean
    public Queue queue(){
        return new Queue("myqueue");
    }
    @Bean
    public TopicExchange exchange(){
        return new TopicExchange("javaExchange");
    }
    @Bean
    public Binding binding(){
    return BindingBuilder.bind(queue()).to(exchange()).with("routingkey");
    }
}
