package zalando.gateway.redis;

import com.shared.dto.session.Session;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;


import java.util.concurrent.TimeUnit;

@Service
@AllArgsConstructor
public class RedisUtility {
    private final RedisTemplate<String, Session> redisTemplate;
    public Session getValue(final String userID) {
        Session session = redisTemplate.opsForValue().get("sessions::" + userID);
        return session;
    }
}