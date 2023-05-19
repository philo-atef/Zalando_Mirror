package net.example.rabbitmq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQProducer {
//    @Value("${rabbitmq.exchange.name}")
    private String exchange = "inventory_service_exchange";

//    @Value("${rabbitmq.json_routing_key.name}")
    private String jsonRoutingKey = "inventory_service_getProductInventory_routing_key";

    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitTemplate.class);

    private RabbitTemplate rabbitTemplate;

    public RabbitMQProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendJsonMessage(String message){
        LOGGER.info(String.format("Json message sent -> %s", message.toString()));

        Object response = rabbitTemplate.convertSendAndReceive(exchange, jsonRoutingKey, message);
        LOGGER.info((String) response);
    }
}