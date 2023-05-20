package zalando.inventoryservice.rabbitmq;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import zalando.inventoryservice.service.InventoryService;

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

}