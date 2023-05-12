package com.example.demo.merchant;

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
public class MerchantService {
    private final MerchantRepository merchantRepository;
    private final TokenRepository tokenRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @GetMapping
    public List<Merchant> getMerchants(){
        return merchantRepository.findAll();
    }

    @Transactional
    public void editMerchantProfile(MerchantEditRequest merchantEditRequest, HttpServletRequest request) {
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
            if(merchantEditRequest.getEmail() != null && merchantEditRequest.getEmail().length() > 0 &&
                    !merchantEditRequest.getEmail().equals(user.getEmail())){
                Optional<User> userWithThisEmail = userRepository.findUserByEmail(merchantEditRequest.getEmail());
                if(userWithThisEmail.isPresent()){
                    throw new IllegalStateException("Email already taken");
                }
                user.setEmail(merchantEditRequest.getEmail());
            }
            if(merchantEditRequest.getPassword() != null && merchantEditRequest.getPassword().length() > 0){
                user.setPassword(passwordEncoder.encode(merchantEditRequest.getPassword()));
            }
            userRepository.save(user);

            var merchantToBeEdited = merchantRepository.findMerchantById(user.getId());
            if(merchantEditRequest.getBrandName() != null && merchantEditRequest.getBrandName().length() > 0){
                merchantToBeEdited.get().setBrandName(merchantEditRequest.getBrandName());
            }
            if(merchantEditRequest.getHotline() != null && merchantEditRequest.getHotline().length() > 0){
                merchantToBeEdited.get().setHotline(merchantEditRequest.getHotline());
            }
            merchantRepository.save(merchantToBeEdited.get());
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
            tokenRepository.deleteAll(tokensToBeDeleted);
            merchantRepository.deleteById(userIDToBeDeleted);
            userRepository.deleteById(userIDToBeDeleted);
        }
    }
}
