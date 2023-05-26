package com.example.demo.rabbitMQ;

import com.example.demo.customer.CustomerService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RabbitMQConsumer {
    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQConsumer.class);
    private final CustomerService customerService;

    @RabbitListener(queues = "userDetailsQueue")
    public Object  getUserID(String userID){
        LOGGER.info(String.format("Received User ID-> %s",userID));
        System.out.println(userID);
        Object result = customerService.getCustomerByID(Long.parseLong(userID));
        LOGGER.info(String.format("Received User -> %s",result.toString()));
        return (result);
    }
}
