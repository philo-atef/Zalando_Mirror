package zalando.authentication.auth;

import zalando.authentication.customer.CustomerRegisterRequest;
import zalando.authentication.merchant.MerchantRegisterRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
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

    @PostMapping("/logout")
    public ResponseEntity<LogoutResponse> logout(
            HttpServletRequest request
    ) {
        return ResponseEntity.ok(authenticationService.logout(request));
    }
}