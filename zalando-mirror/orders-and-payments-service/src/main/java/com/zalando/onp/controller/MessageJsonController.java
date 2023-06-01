package com.zalando.onp.controller;

import com.shared.dto.order.*;

import com.zalando.onp.publisher.RabbitMQJsonProducer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class MessageJsonController {
    private RabbitMQJsonProducer jsonProducer;

    public MessageJsonController(RabbitMQJsonProducer jsonProducer) {
        this.jsonProducer = jsonProducer;
    }

    @PostMapping("/publish")
    public ResponseEntity<String> sendJsonMessage(@RequestBody OrderResponse orderResponse){
        jsonProducer.sendJsonMessage(orderResponse);
        return ResponseEntity.ok("Json message sent to RabbitMQ ...");
    }

    @PostMapping("/getUserEmail")
    public Object testAuth(@RequestBody authUserId user){
        System.out.println("Message Controller : " + user.getUserId());
        return jsonProducer.sendAuthRequest(user.getUserId());
    }
}
