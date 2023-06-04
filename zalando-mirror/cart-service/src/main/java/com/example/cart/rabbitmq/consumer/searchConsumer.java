
package com.example.cart.rabbitmq.consumer;

import com.example.cart.model.Cart;
import com.example.cart.service.CartService;
import com.shared.dto.search.SearchRequest;
import com.shared.dto.search.SearchResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
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
            Cart newCart = cartService.addCartItem("1", searchRequest );
            SearchResponse response = cartService.formatSearchResponse(newCart, searchRequest);
            return response ;
        }
    catch (Exception e){
        return new SearchResponse(null,null,null,null,false);
    }

    }

}

