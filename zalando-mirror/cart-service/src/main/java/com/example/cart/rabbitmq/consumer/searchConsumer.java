package com.example.cart.rabbitmq.consumer;

import com.example.cart.dto.SearchRequest;
import com.example.cart.dto.SearchResponse;
import com.example.cart.model.Cart;
import com.example.cart.service.CartService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.UUID;

public class searchConsumer {
    private static  final Logger LOGGER = LoggerFactory.getLogger(searchConsumer.class);
    private final CartService cartService;
    private RedisTemplate<String, Object> redisTemplate;

    public searchConsumer(CartService cartService) {
        this.cartService = cartService;
    }

    @RabbitListener(queues = {"${rabbitmq.search.queue.name}"})
    public Object consume(SearchRequest searchRequest)
    {
        UUID userId = (UUID)redisTemplate.opsForValue().get("session");

        Cart newCart = cartService.addCartItem(userId, searchRequest);

        LOGGER.info(String.format("Received message -> %s", searchRequest.toString()));

        SearchResponse response = cartService.formatSearchResponse(newCart,searchRequest);

        return response ;

    }
}
