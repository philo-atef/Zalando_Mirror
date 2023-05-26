package com.example.demo.redis;

import com.example.demo.session.Session;
import com.example.demo.token.Token;
import com.example.demo.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RedisService {
    @Cacheable(cacheNames = "sessions", key = "#token.getId()")
    public Session createSession(Token token){
        User user = token.getUser();
        Session session = new Session();
        session.setToken(token.getToken());
        session.setRole(user.getRole());
        session.setUserID(user.getId());
        return session;
    }
    @CacheEvict(cacheNames = "sessions", key = "#token.getId()")
    public void deleteSession(Token token){
    }
}
