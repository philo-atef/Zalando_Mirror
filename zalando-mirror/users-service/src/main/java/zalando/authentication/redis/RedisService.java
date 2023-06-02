package zalando.authentication.redis;

import zalando.authentication.token.Token;
import zalando.authentication.user.User;
import com.shared.dto.session.Session;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RedisService {
    @Cacheable(cacheNames = "sessions", key = "#token.getUser().getId()")
    public Session createSession(Token token){
        User user = token.getUser();
        Session session = new Session();
        session.setToken(token.getToken());
        session.setRole(user.getRole());
        session.setUserID(user.getId());
        return session;
    }
    @CacheEvict(cacheNames = "sessions", key = "#token.getUser().getId()")
    public void deleteSession(Token token){
    }
}
