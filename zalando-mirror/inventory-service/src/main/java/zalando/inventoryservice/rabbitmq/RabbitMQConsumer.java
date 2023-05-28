package zalando.inventoryservice.rabbitmq;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shared.dto.inventory.CreateInventoryItemRequest;
import com.shared.dto.inventory.InventoryItemResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import zalando.inventoryservice.dto.CreateItemDto;

import zalando.inventoryservice.dto.InventoryItemsRequest;
import zalando.inventoryservice.dto.UnavailableItemDto;
import zalando.inventoryservice.dto.UnavailableItemsResponse;
import zalando.inventoryservice.service.InventoryService;



import zalando.inventoryservice.model.InventoryItem;


import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RabbitMQConsumer {
    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQConsumer.class);
    private final InventoryService inventoryService;


    @RabbitListener(queues = "cartInventoryQueue")
    public Object  validateCartInvItems(InventoryItemsRequest message){

        System.out.println(message.getClass());
        System.out.println(message.getInventoryItemRequestList().get(0).getSize());
        System.out.println(message.getInventoryItemRequestList().get(0).getColor());
        System.out.println(message.getInventoryItemRequestList().get(0).getProductId());

        List<UnavailableItemDto> result = inventoryService.validateCartContent(message.getInventoryItemRequestList());

        LOGGER.info(String.format("Received User -> %s", result));

        UnavailableItemsResponse r = new UnavailableItemsResponse(result);

        ObjectMapper mapper = new ObjectMapper();

        mapper.convertValue(
                result,
                new TypeReference<List<UnavailableItemDto>>(){}
        );

        return result;
    }

    @RabbitListener(queues = "productInvQueue")
    public Object  getProductInvItems(MessageWrapper message){
            LOGGER.info(String.format("Received message -> %s", message.toString()));

            List<InventoryItem> result = inventoryService.getProductInventory((String) message.getPayload());
            List<InventoryItemResponse> response = result.stream()
                    .map(item -> InventoryItemResponse.builder()
                            .sku(item.getSku())
                            .productId(item.getProductId())
                            .color(item.getColor())
                            .size(item.getSize())
                            .quantity(item.getQuantity())
                            .build())
                    .collect(Collectors.toList());

            return ( new MessageWrapper("5od",response));
    }

    @RabbitListener(queues = "bulkCreateInvItems")
    public Object createInventoryItem(List<CreateInventoryItemRequest> message){
        LOGGER.info(String.format("Received Inventory items -> %s", message.toString()));
        List<InventoryItem> createdInventoryItems = inventoryService.bulkCreateInventoryItem(message);

        List<InventoryItemResponse> response = createdInventoryItems.stream()
                .map(item -> InventoryItemResponse.builder()
                        .sku(item.getSku())
                        .productId(item.getProductId())
                        .color(item.getColor())
                        .size(item.getSize())
                        .quantity(item.getQuantity())
                        .build())
                .collect(Collectors.toList());

        return response;
    }
}