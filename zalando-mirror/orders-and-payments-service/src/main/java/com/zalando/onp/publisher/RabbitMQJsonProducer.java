package com.zalando.onp.publisher;

import com.zalando.onp.dto.Cart;
import com.zalando.onp.dto.OrderResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQJsonProducer {
    @Value("${rabbitmq.exchange.name}")
    private String exchange;
    @Value("${rabbitmq.routing.json.key}")
    private String routingJsonKey;
    @Value("${rabbitmq.auth.routing.key}")
    private String authRoutingKey;

    private static final Logger LOGGER= LoggerFactory.getLogger(RabbitMQJsonProducer.class);

    private RabbitTemplate rabbitTemplate;

    public RabbitMQJsonProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendJsonMessage(OrderResponse orderResponse){
        LOGGER.info(String.format("Json message sent -> %s", orderResponse.toString()));
        rabbitTemplate.convertAndSend(exchange, routingJsonKey, orderResponse);
    }

    public Object sendAuthRequest(String userId){
        LOGGER.info(String.format("Json message sent -> %s", userId));
        return rabbitTemplate.convertSendAndReceive(exchange, authRoutingKey, userId);
    }

}
