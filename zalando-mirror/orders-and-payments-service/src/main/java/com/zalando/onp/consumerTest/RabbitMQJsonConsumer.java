package com.zalando.onp.consumerTest;

import com.shared.dto.order.Cart;
import com.shared.dto.order.OrderResponse;
import com.zalando.onp.controller.OrderController;

import com.zalando.onp.model.Order;
import com.zalando.onp.publisher.RabbitMQJsonProducer;
import com.zalando.onp.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class RabbitMQJsonConsumer {
    Order receivedOrder;
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderController orderController;
    private static final Logger LOGGER= LoggerFactory.getLogger(RabbitMQJsonConsumer.class);
    private RabbitMQJsonProducer jsonProducer;
    //UNCOMMENT THIS!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
//    public RabbitMQJsonConsumer(RabbitMQJsonProducer jsonProducer) {
//        this.jsonProducer = jsonProducer;
//    }
//    @RabbitListener(queues = {"${rabbitmq.queue.json.name}"})
//    public OrderResponse consumeJsonMessage(Cart cart){
//        LOGGER.info(String.format("Received JSON message -> %s", cart.toString()));
//
//        orderController.cartToOrder(cart, null, null);
//        //cartToOrder(cart);
////        List<AuthResponse> authResponses = (List<AuthResponse>) (jsonProducer.sendAuthRequest(cart.getUserID()));
////        AuthResponse userDetails = authResponses.get(0);
////        String shipAdd =userDetails.getAddress();
////        String creditNum = userDetails.getCreditCardNumber();
//        //orderRepository.save(receivedOrder);
////        orderController.everythingOrderRelated(cart,shipAdd,creditNum);
//        return null ;
//    }

    public void cartToOrder(Cart cart){
        receivedOrder = new Order(Long.parseLong(cart.getUserID()), null, null, null, cart.getTotalPrice(), cart.getCartItemsList(), "pending");

    }

// put here functions from the controller
    /**
     * @RabbitListener(queues = "SearchCart")
     *     public Object consume(SearchRequest searchRequest)
     *     {try {
     *
     *
     * //        UUID userId = (UUID)redisTemplate.opsForValue().get("session");
     *         UUID userId =new UUID(0x12345678L, 0x90ABCDEF);
     * //Object response=((MessageWrapper) searchRequest).getPayload();
     * //        LOGGER.info(String.format("Producer received response -> %s", response.getClass()));
     * //        LOGGER.info(String.format("Producer received response -> %s", response));
     * //        LOGGER.info(String.format("Producer received response -> %s",(((LinkedHashMap) response).keySet())+""+((LinkedHashMap) response).values()));
     * //        MessageWrapper responseWrapper = new MessageWrapper();
     * //        if (response instanceof LinkedHashMap) {
     * //            LinkedHashMap<String, Object> responseMap = (LinkedHashMap<String, Object>) response;
     * //
     * //            responseWrapper.setType((String) responseMap.get("type"));
     * //            responseWrapper.setPayload(responseMap.get("payload"));
     * //            response = responseWrapper;
     * //            LOGGER.info(String.format("Producer received response -> %s", response));
     * ////            return (responseWrapper.getPayload());
     * //        }
     *         Cart newCart = cartService.addCartItem(userId.toString(),searchRequest );
     *
     *         LOGGER.info(String.format("Received message -> %s", searchRequest.toString()));
     *
     *         SearchResponse response2 = cartService.formatSearchResponse(newCart, searchRequest);
     *         ArrayList<SearchResponse> res= new ArrayList<SearchResponse>();
     *
     * //        Object c= res.get(1);
     *        res.add(response2);
     *         return res ;}
     *     catch (Exception e){
     *         return new SearchResponse(null,null,null,null,false);
     *     }
     *
     *     }
     *
     *
     *
     * **/
}
