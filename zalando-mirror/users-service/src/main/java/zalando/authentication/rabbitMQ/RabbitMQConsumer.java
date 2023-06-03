package zalando.authentication.rabbitMQ;

import com.shared.dto.order.UserDetails;
import zalando.authentication.customer.CustomerService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RabbitMQConsumer {
    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQConsumer.class);
    private final CustomerService customerService;

    @RabbitListener(queues = "userDetailsQueue")
    public UserDetails getCustomerDetails(String userID){
        LOGGER.info(String.format("Received User ID -> %s", userID));
        UserDetails result = customerService.getCustomerDetailsByID(Long.parseLong(userID));
        LOGGER.info(String.format("Received User Details -> %s", result.toString()));
        return (result);
    }

}
