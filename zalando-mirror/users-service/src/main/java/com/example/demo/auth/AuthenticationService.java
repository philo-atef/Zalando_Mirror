package com.example.demo.auth;

import com.example.demo.config.JwtService;
import com.example.demo.customer.Customer;
import com.example.demo.customer.CustomerRegisterRequest;
import com.example.demo.customer.CustomerRepository;
import com.example.demo.merchant.Merchant;
import com.example.demo.merchant.MerchantRegisterRequest;
import com.example.demo.merchant.MerchantRepository;
import com.example.demo.redis.RedisService;
import com.example.demo.token.Token;
import com.example.demo.token.TokenRepository;
import com.example.demo.token.TokenType;
import com.example.demo.user.Role;
import com.example.demo.user.User;
import com.example.demo.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final CustomerRepository customerRepository;
    private final MerchantRepository merchantRepository;
    private final RedisService redisService;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;


    public AuthenticationResponse registerCustomer(CustomerRegisterRequest request){
        Optional<User> userWithThisEmail = userRepository.findUserByEmail(request.getEmail());
        if(userWithThisEmail.isPresent()){
            throw new IllegalStateException("Email already taken");
        }

        var user = User.builder()
                .email(request.getEmail())
                .role(Role.Customer)
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        Customer customer = new Customer();
        customer.setFirstName(request.getFirstName());
        customer.setLastName(request.getLastName());
        customer.setAddress(request.getAddress());
        customer.setDateOfBirth(request.getDateOfBirth());
        customer.setTelephoneNumber(request.getTelephoneNumber());
        customer.setCreditCardNumber(request.getCreditCardNumber());
        customer.setUserDetails1(user);
        customerRepository.save(customer);

        var savedUser = userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        Token token = saveUserToken(savedUser, jwtToken);
        redisService.createSession(token);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .message("Registered successfully")
                .build();
    }


    public AuthenticationResponse registerMerchant(MerchantRegisterRequest request){
        Optional<User> userWithThisEmail = userRepository.findUserByEmail(request.getEmail());
        if(userWithThisEmail.isPresent()){
            throw new IllegalStateException("Email already taken");
        }

        var user = User.builder()
                .email(request.getEmail())
                .role(Role.Merchant)
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        Merchant merchant = new Merchant();
        merchant.setBrandName(request.getBrandName());
        merchant.setDateJoined(request.getDateJoined());
        merchant.setHotline(request.getHotline());
        merchant.setUserDetails2(user);
        merchantRepository.save(merchant);

        var savedUser = userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        Token token = saveUserToken(savedUser, jwtToken);
        redisService.createSession(token);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .message("Registered successfully")
                .build();
    }


    public AuthenticationResponse authenticateCustomer(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = userRepository.findUserByEmail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        revokeAllUserTokens(user);
        Token token = saveUserToken(user, jwtToken);
        redisService.createSession(token);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .message("Logged in successfully")
                .build();
    }

    public AuthenticationResponse authenticateMerchant(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = userRepository.findUserByEmail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        revokeAllUserTokens(user);
        Token token = saveUserToken(user, jwtToken);
        redisService.createSession(token);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .message("Logged in successfully")
                .build();
    }

    private Token saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
        return token;
    }
    private void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllValidTokensByUser(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);

        validUserTokens.forEach(token -> {
            redisService.deleteSession(token);
        });
    }


}
