package com.example.cart.rabbitmq.publisher;

import com.example.cart.dto.InventoryItemsRequest;
import com.example.cart.dto.InventoryResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service
public class inventoryProducer {

    @Value("${rabbitmq.cart.exchange.name}")
    private String exchange ;

    @Value("${rabbitmq.inventory.queue.key}")
    private String routingKey ;

    private RabbitTemplate rabbitTemplate;

    private static final Logger LOGGER =  LoggerFactory.getLogger(inventoryProducer.class);

    public inventoryProducer (RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public InventoryResponse sendMessage(InventoryItemsRequest inventoryItemsRequest)
    {
        LOGGER.info(String.format("Inventory Items in Json was sent -> %s", inventoryItemsRequest.toString()));
        return (InventoryResponse) rabbitTemplate.convertSendAndReceive(exchange, routingKey, inventoryItemsRequest);
    }
}
