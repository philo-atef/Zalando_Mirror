package zalando.productsservice.rabbitmq;

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
    private String exchange;

    @Value("${rabbitmq.routing_key.name}")
    private String routingKey;

    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitTemplate.class);

    private RabbitTemplate rabbitTemplate;

    public RabbitMQProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendMessage(String message ){
        LOGGER.info(String.format("Message sent -> %s", message));
        rabbitTemplate.convertAndSend(exchange, routingKey, message);
    }


    public Object bulkCreateInventoryItems(MessageWrapper message){
        LOGGER.info(String.format("Json message sent -> %s", message.toString()));
        Object response = rabbitTemplate.convertSendAndReceive(exchange, routingKey, message);

        if(response instanceof List){
            List<LinkedHashMap<String, Object>> createdInvItems = (List) response;
            for(LinkedHashMap<String, Object> invItem : createdInvItems){
                LOGGER.info(String.format("Inventory Service: Created Inventory item => %s", invItem.toString()));
            }
        }

        return response;

    }
}
