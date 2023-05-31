
package com.example.cart.rabbitmq.consumer;

import com.shared.dto.search.*;
import com.shared.dto.cart.*;
import com.shared.dto.inventory.*;
import com.example.cart.model.Cart;
import com.example.cart.service.CartService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
//import org.springframework.data.redis.core.RedisTemplate;

import java.util.ArrayList;
import java.util.UUID;
@Service
public class searchConsumer {
    private static  final Logger LOGGER = LoggerFactory.getLogger(searchConsumer.class);
    private final CartService cartService;

    public searchConsumer(CartService cartService) {
        this.cartService = cartService;
    }

    @RabbitListener(queues = "SearchCart")
    public SearchResponse consume(SearchRequest searchRequest)
    {
        try {
            LOGGER.info(String.format("Received message -> %s", searchRequest.toString()));
            Cart newCart = cartService.addCartItem("1234567", searchRequest );
            SearchResponse response = cartService.formatSearchResponse(newCart, searchRequest);
            return response ;
        }
    catch (Exception e){
        return new SearchResponse(null,null,null,null,false);
    }

    }

}

