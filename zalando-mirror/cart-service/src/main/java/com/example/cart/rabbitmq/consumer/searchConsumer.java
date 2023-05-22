//package com.example.cart.rabbitmq.consumer;
//
//import com.example.cart.dto.SearchRequest;
//import com.example.cart.dto.SearchResponse;
//import com.example.cart.model.Cart;
//import com.example.cart.service.CartService;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.amqp.rabbit.annotation.RabbitListener;
//import org.springframework.stereotype.Service;
//
//import java.util.ArrayList;
//import java.util.LinkedHashMap;
//import java.util.UUID;
//
//
//@Service
//public class searchConsumer {
//    private static  final Logger LOGGER = LoggerFactory.getLogger(searchConsumer.class);
//    private final CartService cartService;
////    private RedisTemplate<String, Object> redisTemplate;
//
//    public searchConsumer(CartService cartService) {
//        this.cartService = cartService;
//    }
//
//    @RabbitListener(queues = "SearchCart")
//    public Object consume(SearchRequest searchRequest)
//    {
////        UUID userId = (UUID)redisTemplate.opsForValue().get("session");
//
//        // Dummy userId
//        UUID userId =new UUID(0x12345678L, 0x90ABCDEF);
//
//        Cart newCart = cartService.addCartItem(userId.toString(),searchRequest);
//
//        LOGGER.info(String.format("Received message -> %s", searchRequest.toString()));
//
//        SearchResponse searchResponse = cartService.formatSearchResponse(newCart, searchRequest);
//        ArrayList<SearchResponse> response= new ArrayList<>();
//        response.add(searchResponse);
//
//        return response ;
//
//    }
//
//}
