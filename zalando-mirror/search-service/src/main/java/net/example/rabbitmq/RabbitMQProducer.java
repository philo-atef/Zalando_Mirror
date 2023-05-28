package net.example.rabbitmq;

import com.shared.dto.search.*;
import com.shared.dto.inventory.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.UUID;

@Service
public class RabbitMQProducer {
//    @Value("${rabbitmq.exchange.name}")
    private final String exchange = "inventoryServiceExchange";

//    @Value("${rabbitmq.json_routing_key.name}")
    private final String jsonRoutingKey = "getProductInventoryRoutingKey";

    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitTemplate.class);

    private final RabbitTemplate rabbitTemplate;

    public RabbitMQProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

        public Object sendToInventory(String message ,String exchange,String routingKey){
            LOGGER.info(String.format("Json message sent -> %s", message));
          //rabbitTemplate.convertAndSend(exchange, jsonRoutingKey, message);
            List<InventoryItemResponse> response = (List<InventoryItemResponse>) rabbitTemplate.convertSendAndReceive(exchange, routingKey, message);
//            if (response instanceof LinkedHashMap) {
//                LinkedHashMap<String, Object> responseMap = (LinkedHashMap<String, Object>) response;
//                MessageWrapper responseWrapper = new MessageWrapper();
//                responseWrapper.setType((String) responseMap.get("type"));
//                responseWrapper.setPayload(responseMap.get("payload"));
//                response = responseWrapper;
//                LOGGER.info(String.format("Producer received response -> %s", response));
//                return (responseWrapper.getPayload());
//            }
            LOGGER.info(String.format("Producer received response -> %s", response.getClass()));

            LOGGER.info(String.format("Producer received response -> %s", response));
            LOGGER.info(String.format("Producer received response -> %s", response.getClass()));
            return response;
            }

    public Object addToCart(SearchRequest message , String exchange, String routingKey){
        LOGGER.info(String.format("Json message sent -> %s", message.toString()));
        //rabbitTemplate.convertAndSend(exchange, jsonRoutingKey, message);\

        List<SearchResponse> response =  (List<SearchResponse>)rabbitTemplate.convertSendAndReceive(exchange, routingKey, message);
        LOGGER.info(String.format("Producer received response -> %s", response.getClass()));
        LOGGER.info(String.format("Producer received response -> %s", response));
        if(response==null)
            return null;
//        if (response instanceof LinkedHashMap) {
//            LinkedHashMap<String, Object> responseMap = (LinkedHashMap<String, Object>) response;
//            SearchResponse responseWrapper = new SearchResponse();
//            responseWrapper.setProductID((String) responseMap.get("productID"));
//            responseWrapper.setColor((String) responseMap.get("color"));
//            responseWrapper.setSize((String) responseMap.get("size"));
//            responseWrapper.setQuantity((Integer) responseMap.get("quantity"));
//            responseWrapper.setAdded((boolean) responseMap.get("added"));
//
//            response = responseWrapper;
//            LOGGER.info(String.format("Producer received response -> %s", response));
//            return responseWrapper;
//        }

        LOGGER.info(String.format("Producer received response -> %s", response));
        LOGGER.info(String.format("Producer received response -> %s", response.getClass()));
        return response;
    }
    public void sendMessagetoQueue(MessageWrapper message ,String exchange,String routingKey){
        LOGGER.info(String.format("Json message sent -> %s", message.toString()));
        //rabbitTemplate.convertAndSend(exchange, jsonRoutingKey, message);
         rabbitTemplate.convertAndSend(exchange, routingKey, message);



    }

}