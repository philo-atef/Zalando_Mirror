package zalando.inventoryservice.rabbitmq;


import com.shared.dto.inventory.CreateInventoryItemRequest;
import com.shared.dto.inventory.InventoryItemRequest;
import com.shared.dto.inventory.InventoryItemResponse;

import com.shared.dto.inventory.UnavailableItemResponse;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import zalando.inventoryservice.dto.*;


import zalando.inventoryservice.service.InventoryService;

import zalando.inventoryservice.model.InventoryItem;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RabbitMQConsumer {
    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQConsumer.class);
    private final InventoryService inventoryService;


    @RabbitListener(queues = "cartInventoryQueue")
    public List<UnavailableItemResponse>  validateCartInvItems(List<InventoryItemRequest> inventoryItemRequests){
        List<CartItemDto> cartItemDtos = inventoryItemRequests.stream()
                .map(inventoryItemRequest -> CartItemDto.builder()
                        .productId(inventoryItemRequest.getProductId())
                        .color(inventoryItemRequest.getColor())
                        .size(inventoryItemRequest.getSize())
                        .quantity(inventoryItemRequest.getQuantity())
                        .build())
                .collect(Collectors.toList());

        List<UnavailableItemDto> unavailableItemDtos = inventoryService.validateCartContent(cartItemDtos);
        List<UnavailableItemResponse> unavailableItemResponses = unavailableItemDtos.stream()
                .map(unavailableItemDto -> UnavailableItemResponse.builder()
                        .productId(unavailableItemDto.getProductId())
                        .color(unavailableItemDto.getColor())
                        .size(unavailableItemDto.getColor())
                        .availableQuantity(unavailableItemDto.getAvailableQuantity())
                        .requestedQuantity(unavailableItemDto.getRequestedQuantity())
                        .build())
                .collect(Collectors.toList());

        LOGGER.info(String.format("Received User -> %s", unavailableItemResponses));

        return unavailableItemResponses;
    }


    @RabbitListener(queues = "productInvQueue")
    public Object  getProductInvItems(String message){
            LOGGER.info(String.format("Received message -> %s", message));

            List<InventoryItem> result = inventoryService.getProductInventory((String) message);
            List<InventoryItemResponse> response = result.stream()
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

    @RabbitListener(queues = "bulkCreateInvItems")
    public Object createInventoryItem(List<CreateInventoryItemRequest> createInventoryItemRequests){
        LOGGER.info(String.format("Received Inventory items -> %s", createInventoryItemRequests));

        List<CreateItemDto> createItemDtos = createInventoryItemRequests.stream()
                .map(createInventoryItemRequest -> CreateItemDto.builder()
                        .productId(createInventoryItemRequest.getProductId())
                        .color(createInventoryItemRequest.getColor())
                        .size(createInventoryItemRequest.getSize())
                        .quantity(createInventoryItemRequest.getQuantity())
                        .build())
                .collect(Collectors.toList());

        List<InventoryItem> createdInventoryItems = inventoryService.bulkCreateInventoryItem(createItemDtos);

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

//    @RabbitListener(queues = "inventory_changeQuantityBy")
//    public Object changeQuantityBy(List<InventoryItemRequest> inventoryItemRequests){
//        LOGGER.info(String.format("Received Cart items -> %s", inventoryItemRequests.toString()));
//
//        List<CartItemDto> cartItemDtos = inventoryItemRequests.stream()
//                        .map(inventoryItemRequest -> CartItemDto.builder()
//                                .productId(inventoryItemRequest.getProductId())
//                                .color(inventoryItemRequest.getColor())
//                                .size(inventoryItemRequest.getSize())
//                                .quantity(inventoryItemRequest.getQuantity())
//                                .build())
//                .collect(Collectors.toList());
//
//        List<InventoryItem> updatedInventoryItems = inventoryService.changeQuantityBy(cartItemDtos);
//
//        List<InventoryItemResponse> response = updatedInventoryItems.stream()
//                .map(item -> InventoryItemResponse.builder()
//                        .sku(item.getSku())
//                        .productId(item.getProductId())
//                        .color(item.getColor())
//                        .size(item.getSize())
//                        .quantity(item.getQuantity())
//                        .build())
//                .collect(Collectors.toList());
//
//        return response;
//    }
}