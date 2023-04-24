package net.example.springboot.controller;

import net.example.springboot.publisher.Sender;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class MessageController {

    private Sender producer;

    public MessageController(Sender producer) {
        this.producer = producer;
    }
//http://localhost:8080/api/v1/publish?message=hello

    @GetMapping("/publish")
    public ResponseEntity<String> sendMessage(@RequestParam("message") String message) {

        producer.sendMessage(message);
        return ResponseEntity.ok("Message sent to RabbitMQ");
    }
}