package zalando.inventoryservice.rabbitmq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQConsumer {
    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQConsumer.class);

    @RabbitListener(queues = "inventory_service_getProductInventory_queue")
    public String consumeJsonMessage(String message){
            LOGGER.info(String.format("Received User -> %s", message));
            return "5od";
    }
}