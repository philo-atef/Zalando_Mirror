package com.example.cart.rabbitmq.publisher;

import com.example.cart.dto.InventoryItemsRequest;
import com.example.cart.dto.UnavailableItemDto;
import com.example.cart.dto.UnavailableItemsResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

    public List<UnavailableItemDto> sendMessage(InventoryItemsRequest inventoryItemsRequest)
    {
        LOGGER.info(String.format("Inventory Items in Json was sent -> %s", inventoryItemsRequest.toString()));
        return (List<UnavailableItemDto>) rabbitTemplate.convertSendAndReceive(exchange, routingKey, inventoryItemsRequest);
    }
}
