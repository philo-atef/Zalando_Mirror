package com.example.cart.rabbitmq.publisher;

import com.example.cart.dto.OrderRequest;
import com.example.cart.dto.OrderResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class orderAndPaymentProducer {

    @Value("${rabbitmq.cart.exchange.name}")
    private String exchange ;

    @Value("${rabbitmq.orderAndPayment.queue.key}")
    private String routingKey ;

    private RabbitTemplate rabbitTemplate;

    private static final Logger LOGGER =  LoggerFactory.getLogger(orderAndPaymentProducer.class);

    public orderAndPaymentProducer (RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public OrderResponse sendMessage(OrderRequest orderRequest)
    {
        LOGGER.info(String.format("Cart in Json was sent -> %s", orderRequest.toString()));
        return (OrderResponse) rabbitTemplate.convertSendAndReceive(exchange, routingKey, orderRequest);
    }

}
