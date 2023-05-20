package zalando.inventoryservice.rabbitmq;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import zalando.inventoryservice.dto.CreateItemDto;
import zalando.inventoryservice.service.InventoryService;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RabbitMQConsumer {
    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQConsumer.class);
    private final InventoryService inventoryService;


    @RabbitListener(queues = "productInvQueue")
    public Object  consumeJsonMessage(MessageWrapper message){

            Object result =inventoryService.getProductInventory((String) message.getPayload());
            LOGGER.info(String.format("Received User -> %s", message.toString()));
            return ( new MessageWrapper("5od",result));
    }

    @RabbitListener(queues = "bulkCreateInvItems")
    public void  createInventoryItem(MessageWrapper message){
        LOGGER.info(String.format("Received Inventory items -> %s", message.toString()));

        List<LinkedHashMap<String, Object>> payload = (List<LinkedHashMap<String, Object>>) message.getPayload();
        List<CreateItemDto> createItemDtos = new ArrayList<CreateItemDto>();

        for (LinkedHashMap<String, Object> itemData : payload) {
            CreateItemDto itemDto = new CreateItemDto();

            // Extract the data from the LinkedHashMap and set it in the CreateItemDto object
            itemDto.setProductId((String) itemData.get("productId"));
            itemDto.setColor((String) itemData.get("color"));
            itemDto.setSize((String) itemData.get("size"));
            itemDto.setQuantity((int) itemData.get("quantity"));

            // Add the CreateItemDto object to the list
            createItemDtos.add(itemDto);
        }

        inventoryService.bulkCreateInventoryItem(createItemDtos);
    }
}