package com.example.demo.auth;

import com.example.demo.customer.CustomerRegisterRequest;
import com.example.demo.merchant.MerchantRegisterRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/customer/register")
    public ResponseEntity<AuthenticationResponse> registerCustomer(
            @Valid @RequestBody CustomerRegisterRequest request, BindingResult bindingResult
    ) {
        if(bindingResult.hasErrors()){
            AuthenticationResponse errorResponse = new AuthenticationResponse();
            errorResponse.setMessage("Empty fields");
            return ResponseEntity.badRequest().body(errorResponse);
        }

        return ResponseEntity.ok(authenticationService.registerCustomer(request));
    }
    @PostMapping("/merchant/register")
    public ResponseEntity<AuthenticationResponse> registerMerchant(
            @Valid @RequestBody MerchantRegisterRequest request, BindingResult bindingResult
    ) {
        if(bindingResult.hasErrors()){
            AuthenticationResponse errorResponse = new AuthenticationResponse();
            errorResponse.setMessage("Empty fields");
            return ResponseEntity.badRequest().body(errorResponse);
        }

        return ResponseEntity.ok(authenticationService.registerMerchant(request));
    }

    @PostMapping("/customer/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticateCustomer(
            @RequestBody AuthenticationRequest request
    ) {
        return ResponseEntity.ok(authenticationService.authenticateCustomer(request));
    }

    @PostMapping("/merchant/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticateMerchant(
            @RequestBody AuthenticationRequest request
    ) {
        return ResponseEntity.ok(authenticationService.authenticateMerchant(request));
    }
}