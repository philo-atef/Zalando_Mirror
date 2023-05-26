
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
import org.springframework.stereotype.Service;
//import org.springframework.data.redis.core.RedisTemplate;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.UUID;
@Service
public class searchConsumer {
    private static  final Logger LOGGER = LoggerFactory.getLogger(searchConsumer.class);
    private final CartService cartService;
//    private RedisTemplate<String, Object> redisTemplate;

    public searchConsumer(CartService cartService) {
        this.cartService = cartService;
    }

    @RabbitListener(queues = "SearchCart")
    public Object consume(SearchRequest searchRequest)
    {try {


//        UUID userId = (UUID)redisTemplate.opsForValue().get("session");
        UUID userId =new UUID(0x12345678L, 0x90ABCDEF);
//Object response=((MessageWrapper) searchRequest).getPayload();
//        LOGGER.info(String.format("Producer received response -> %s", response.getClass()));
//        LOGGER.info(String.format("Producer received response -> %s", response));
//        LOGGER.info(String.format("Producer received response -> %s",(((LinkedHashMap) response).keySet())+""+((LinkedHashMap) response).values()));
//        MessageWrapper responseWrapper = new MessageWrapper();
//        if (response instanceof LinkedHashMap) {
//            LinkedHashMap<String, Object> responseMap = (LinkedHashMap<String, Object>) response;
//
//            responseWrapper.setType((String) responseMap.get("type"));
//            responseWrapper.setPayload(responseMap.get("payload"));
//            response = responseWrapper;
//            LOGGER.info(String.format("Producer received response -> %s", response));
////            return (responseWrapper.getPayload());
//        }
        Cart newCart = cartService.addCartItem(userId.toString(),searchRequest );

        LOGGER.info(String.format("Received message -> %s", searchRequest.toString()));

        SearchResponse response2 = cartService.formatSearchResponse(newCart, searchRequest);
        ArrayList<SearchResponse> res= new ArrayList<SearchResponse>();

//        Object c= res.get(1);
//        res.add(response2);
        return response2 ;}
    catch (Exception e){
        return new SearchResponse(null,null,null,null,false);
    }

    }

}

