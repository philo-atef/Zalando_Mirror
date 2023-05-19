package net.example.rabbitmq;

import net.example.dto.InventoryItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;

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

        public ArrayList<InventoryItem> getProductItems(MessageWrapper message){
            LOGGER.info(String.format("Json message sent -> %s", message.toString()));
          //rabbitTemplate.convertAndSend(exchange, jsonRoutingKey, message);
            Object response = rabbitTemplate.convertSendAndReceive(exchange, jsonRoutingKey, message);
            if (response instanceof LinkedHashMap) {
                LinkedHashMap<String, Object> responseMap = (LinkedHashMap<String, Object>) response;
                MessageWrapper responseWrapper = new MessageWrapper();
                responseWrapper.setType((String) responseMap.get("type"));
                responseWrapper.setPayload(responseMap.get("payload"));
                response = responseWrapper;
                LOGGER.info(String.format("Producer received response -> %s", response));
                return ((ArrayList<InventoryItem>)responseWrapper.getPayload());
            }

            LOGGER.info(String.format("Producer received response -> %s", response));
            return null;
            }


}