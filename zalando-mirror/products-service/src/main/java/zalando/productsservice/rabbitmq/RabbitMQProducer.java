package zalando.productsservice.rabbitmq;

import com.shared.dto.inventory.CreateInventoryItemRequest;
import com.shared.dto.inventory.InventoryItemResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import zalando.productsservice.model.Product;

import java.util.LinkedHashMap;
import java.util.List;

@Service
public class RabbitMQProducer {

    @Value("${rabbitmq.exchange.name}")
    private String inventoryServiceExchange;

    @Value("${rabbitmq.routing_key.name}")
    private String bulkCreateRoutingKey;

    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitTemplate.class);

    private RabbitTemplate rabbitTemplate;

    public RabbitMQProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

//    public void sendMessage(String message ){
//        LOGGER.info(String.format("Message sent -> %s", message));
//        rabbitTemplate.convertAndSend(exchange, routingKey, message);
//    }


    public List<InventoryItemResponse> bulkCreateInventoryItems(List<CreateInventoryItemRequest> requestMessage){
        LOGGER.info(String.format("Json message sent -> %s", requestMessage.toString()));
        List<InventoryItemResponse> response = (List<InventoryItemResponse>) rabbitTemplate.convertSendAndReceive(inventoryServiceExchange, bulkCreateRoutingKey, requestMessage);

        return response;
    }
}

