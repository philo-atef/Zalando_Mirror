package com.zalando.onp.consumerTest;

import com.shared.dto.order.OrderResponse;
import com.zalando.onp.dto.Cart;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQJsonConsumer {
    private static final Logger LOGGER= LoggerFactory.getLogger(RabbitMQJsonConsumer.class);

    @RabbitListener(queues = {"${rabbitmq.queue.json.name}"})
    public OrderResponse consumeJsonMessage(Cart cart){
        LOGGER.info(String.format("Received JSON message -> %s", cart.toString()));
        return null ;
    }
// put here functions from the controller
}
