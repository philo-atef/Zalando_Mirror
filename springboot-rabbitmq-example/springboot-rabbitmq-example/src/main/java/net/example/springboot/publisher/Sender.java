package net.example.springboot.publisher;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class Sender {
    private final RabbitTemplate rabbitTemplate;
    public Sender(RabbitTemplate rabbitTemplate){
        this.rabbitTemplate=rabbitTemplate;
    }
    public void sendMessage(String message){
        rabbitTemplate.convertAndSend("javaExchange","routingkey",message);
    }
}
