package com.example.demo.customer;

import com.example.demo.dto.UserDetails;
import com.example.demo.redis.RedisService;
import com.example.demo.token.Token;
import com.example.demo.token.TokenRepository;
import com.example.demo.user.User;
import com.example.demo.user.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final TokenRepository tokenRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RedisService redisService;
    @GetMapping
    public List<Customer> getCustomers(){
        return customerRepository.findAll();
    }

    @Transactional
    public void editCustomerProfile(CustomerEditRequest customerEditRequest, HttpServletRequest request) {
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }
        jwt = authHeader.substring(7);
        var storedToken = tokenRepository.findByToken(jwt)
                .orElse(null);
        if (storedToken != null) {
            var user = storedToken.getUser();
            if(customerEditRequest.getEmail() != null && customerEditRequest.getEmail().length() > 0 &&
                    !customerEditRequest.getEmail().equals(user.getEmail())){
                Optional<User> userWithThisEmail = userRepository.findUserByEmail(customerEditRequest.getEmail());
                if(userWithThisEmail.isPresent()){
                    throw new IllegalStateException("Email already taken");
                }
                user.setEmail(customerEditRequest.getEmail());
            }
            if(customerEditRequest.getPassword() != null && customerEditRequest.getPassword().length() > 0){
                user.setPassword(passwordEncoder.encode(customerEditRequest.getPassword()));
            }
            userRepository.save(user);

            var customerToBeEdited = customerRepository.findCustomerById(user.getId());
            if(customerEditRequest.getFirstName() != null && customerEditRequest.getFirstName().length() > 0){
                customerToBeEdited.get().setFirstName(customerEditRequest.getFirstName());
            }
            if(customerEditRequest.getLastName() != null && customerEditRequest.getLastName().length() > 0){
                customerToBeEdited.get().setLastName(customerEditRequest.getLastName());
            }
            if(customerEditRequest.getAddress() != null && customerEditRequest.getAddress().length() > 0){
                customerToBeEdited.get().setAddress(customerEditRequest.getAddress());
            }
            if(customerEditRequest.getTelephoneNumber() != null && customerEditRequest.getTelephoneNumber().length() > 0){
                customerToBeEdited.get().setTelephoneNumber(customerEditRequest.getTelephoneNumber());
            }
            if(customerEditRequest.getDateOfBirth() != null){
                customerToBeEdited.get().setDateOfBirth(customerEditRequest.getDateOfBirth());
            }
            if(customerEditRequest.getCreditCardNumber() != null){
                customerToBeEdited.get().setCreditCardNumber(customerEditRequest.getCreditCardNumber());
            }
            customerRepository.save(customerToBeEdited.get());
        }
    }

    public void deleteAccount(HttpServletRequest request) {
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }
        jwt = authHeader.substring(7);
        var storedToken = tokenRepository.findByToken(jwt)
                .orElse(null);
        if (storedToken != null) {
            var userIDToBeDeleted = storedToken.getUser().getId();
            List<Token> tokensToBeDeleted = tokenRepository.findAllTokensByUserID(userIDToBeDeleted);
            redisService.deleteSession(storedToken);
            tokenRepository.deleteAll(tokensToBeDeleted);
            customerRepository.deleteById(userIDToBeDeleted);
            userRepository.deleteById(userIDToBeDeleted);
        }
    }

    public UserDetails getCustomerByID(Long ID) {
        var customer= customerRepository.findCustomerById(ID);
        System.out.println("Customer Service");
        System.out.println(ID);
        UserDetails UD =new UserDetails();
        System.out.println(customer.toString());
        UD.setAddress(customer.get().getAddress());
        UD.setId(customer.get().getId());
        UD.setCreditCardNumber(customer.get().getCreditCardNumber());
        return UD;
    }

}
