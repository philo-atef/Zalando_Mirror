package zalando.gateway;

import com.shared.dto.session.Session;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;
import zalando.gateway.redis.RedisUtility;
import zalando.gateway.utils.JwtUtil;

import java.io.IOException;


@Slf4j
@Component
@AllArgsConstructor
@ServletComponentScan
public class AuthenticationFilter  implements WebFilter {
    private final RedisUtility redisUtility;
    private final JwtUtil jwtUtil;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String authHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        String authToken = null;
        Session cachedSession = null;

        String path = request.getPath().value();
        if (path.startsWith("/api/auth/") && !path.startsWith("/api/auth/logout")) {
            return chain.filter(exchange);
        }
        if(authHeader != null){
            authToken = authHeader.substring(7);
            String userID = "";
            try {
                userID = jwtUtil.extractUserID(authToken);
            } catch (MalformedJwtException e) {
                exchange.getResponse().setStatusCode(HttpStatus.BAD_REQUEST);
                return Mono.empty();
            } catch (Exception e) {
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return Mono.empty();
            }
            cachedSession = redisUtility.getValue(userID);

        }
        if (authToken == null || (cachedSession != null && !authToken.equals(cachedSession.getToken()))){
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return Mono.empty();
        }
        exchange.getResponse().setStatusCode(HttpStatus.OK);
        return chain.filter(exchange);
    }
}
