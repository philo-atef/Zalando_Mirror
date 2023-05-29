package com.example.cart.rabbitmq.publisher;

import com.shared.dto.search.*;
import com.shared.dto.cart.*;
import com.shared.dto.inventory.*;
import com.shared.dto.inventory.UnavailableItemDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

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

    public List<UnavailableItemDto> sendMessage(List<InventoryItemRequest> inventoryItemsRequest)
    {
        LOGGER.info(String.format("Inventory Items in Json was sent -> %s", inventoryItemsRequest.toString()));
        return (List<UnavailableItemDto>) rabbitTemplate.convertSendAndReceive(exchange, routingKey, inventoryItemsRequest);
    }
}
