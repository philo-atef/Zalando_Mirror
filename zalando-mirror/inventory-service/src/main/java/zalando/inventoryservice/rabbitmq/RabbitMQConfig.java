package zalando.inventoryservice.rabbitmq;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
//    @Autowired
//    private ConnectionFactory connectionFactory;
//
//    @Value("${rabbitmq.getProductInventory.queue.name}")
//    private String getProductInventoryQueue;
//
//    @Value("${rabbitmq.inventory-service.exchange.name}")
//    private String inventoryServiceExchange;
//
//    @Value("${rabbitmq.getProductInventory.routing_key.name}")
//    private String getProductInventoryRoutingKey;
//
//    @Bean
//    public RabbitAdmin rabbitAdmin() {
//        return new RabbitAdmin(connectionFactory);
//    }
    // spring bean for rabbitmq queues
//    @Bean
//    public Queue queue(){
//        return new Queue("productInvQueue");
//    }
//
//    // spring bean for rabbitmq exchanges
//    @Bean
//    public TopicExchange exchange(){
//        return new TopicExchange("inventoryServiceExchange");
//    }
//
//    // Binding between Queues and Exchanges using routing keys
//    @Bean
//    public Binding binding(){
//        return BindingBuilder
//                .bind(queue())
//                .to(exchange())
//                .with("getProductInventoryRoutingKey");
//    }


    @Bean
    public MessageConverter converter(){
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(converter());

        return rabbitTemplate;
    }
}
