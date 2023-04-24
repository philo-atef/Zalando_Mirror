package net.example.springboot.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class Receiver {
    private static final Logger LOGGER = LoggerFactory.getLogger(Receiver.class);
@RabbitListener(queues = "myqueue")
    public void recieveMessage(String message){
    LOGGER.info("Recieved message: {}", message);
}
    }

