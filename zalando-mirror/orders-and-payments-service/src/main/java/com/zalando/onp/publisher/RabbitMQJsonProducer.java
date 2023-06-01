package com.zalando.onp.publisher;

import com.shared.dto.order.*;
import com.shared.dto.inventory.InventoryItemRequest;
import com.shared.dto.inventory.InventoryItemResponse;
import com.zalando.onp.exception.ResourceNotFoundException;
import com.zalando.onp.model.Order;
import com.zalando.onp.model.Payment;
import com.zalando.onp.repository.OrderRepository;
import com.zalando.onp.repository.PaymentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RabbitMQJsonProducer {
    @Value("${rabbitmq.exchange.name}")
    private String exchange;
    @Value("${rabbitmq.routing.json.key}")
    private String routingJsonKey;
    @Value("${rabbitmq.auth.routing.key}")
    private String authRoutingKey;

    @Value("${rabbitmq.inv.queue.name}")
    private String invQueueName;
    @Value("${rabbitmq.inv.routing.key}")
    private String invRoutingKey;

    private static final Logger LOGGER= LoggerFactory.getLogger(RabbitMQJsonProducer.class);

    private RabbitTemplate rabbitTemplate;
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    public RabbitMQJsonProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendJsonMessage(OrderResponse orderResponse){
        LOGGER.info(String.format("Json message sent -> %s", orderResponse.toString()));
        rabbitTemplate.convertAndSend(exchange, routingJsonKey, orderResponse);
    }

    public Object sendAuthRequest(String userId){
        LOGGER.info(String.format("Json message sent -> %s", userId));
        List<AuthResponse> response =  (List<AuthResponse>) rabbitTemplate.convertSendAndReceive(exchange, authRoutingKey, userId);
        if(response==null)
            return null;

        return response;
    }

    public Object sendInvRequest(Long orderId, int deduct){
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order does not exist with id :" + orderId));
        //Print here
        List<CartItem> products = order.getProducts();
        List<InventoryItemRequest> productsToInventory= new ArrayList<InventoryItemRequest>() ;
        for(int i =0;i<products.size();i++){
            CartItem currentItem = products.get(i);
            InventoryItemRequest itemRequest = new InventoryItemRequest(currentItem.getColor(),currentItem.getSize(),currentItem.getProductID(),currentItem.getQuantity()*deduct);
            productsToInventory.add(itemRequest);
        }
        List<InventoryItemResponse> response =  (List<InventoryItemResponse>) rabbitTemplate.convertSendAndReceive(exchange, invRoutingKey, productsToInventory);
        LOGGER.info(String.format("Json message received from Inventory -> %s", response));
        if(response==null)
            return null;

        return response;

    }
}
