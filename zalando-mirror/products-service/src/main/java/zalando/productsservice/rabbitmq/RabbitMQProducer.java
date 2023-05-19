package zalando.productsservice.rabbitmq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import zalando.productsservice.model.Product;

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

    public void sendJsonMessage(Product product){
        LOGGER.info(String.format("Json message sent -> %s", product.toString()));
        rabbitTemplate.convertAndSend(exchange, routingKey, product);
    }
}
