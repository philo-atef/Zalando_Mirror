package zalando.gateway;
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
public class AuthenticationFilter extends OncePerRequestFilter implements WebFilter {

    private final RedisUtility redisUtility;
    private final JwtUtil jwtUtil;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("Inside Authentication Filter originated by request {}", request.getRequestURI());

        final String authToken = request.getHeader("Authorization");
        String userId = jwtUtil.extractUsername(authToken);
        System.out.print(userId+"---------------------------------------");
        String cachedToken = redisUtility.getValue("sessions::3");

        if (authToken == null || !authToken.equals(cachedToken)) {
            response.sendError(HttpStatus.UNAUTHORIZED.value());
            return;
        }

        filterChain.doFilter(request, response);
    }


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String authHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        String authToken =null;
        String cachedToken=null;
        if(authHeader != null){
            authToken = authHeader.substring(7);
            String userId = jwtUtil.extractUsername(authToken);
            System.out.print(userId + "---------------------------------------");
            cachedToken = redisUtility.getValue("sessions::"+userId);
            System.out.print(cachedToken);
        }

        if (authToken == null || !authToken.equals(cachedToken)) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return Mono.empty();
        }
           exchange.getResponse().setStatusCode(HttpStatus.OK);
        return Mono.empty();
    }
}
