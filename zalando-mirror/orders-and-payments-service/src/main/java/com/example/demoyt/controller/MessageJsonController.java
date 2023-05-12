package com.example.demoyt.controller;

import com.example.demoyt.dto.User;
import com.example.demoyt.publisher.RabbitMQJsonProducer;
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
    public ResponseEntity<String> sendJsonMessage(@RequestBody User user){
        jsonProducer.sendJsonMessage(user);
        return ResponseEntity.ok("Json message sent to RabbitMQ ...");
    }
}
