package com.example.demo.merchant;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/merchant")
public class MerchantController {
    private final MerchantService merchantService;
    @Autowired
    public MerchantController(MerchantService merchantService) {
        this.merchantService = merchantService;
    }

    @GetMapping
    public List<Merchant> getMerchants(){
        return merchantService.getMerchants();
    }

    @PutMapping("/editProfile")
    public void editMerchantProfile(
            @RequestBody MerchantEditRequest merchantEditRequest,
            HttpServletRequest request
    ) {
        merchantService.editMerchantProfile(merchantEditRequest, request);
    }

    @PostMapping("/deleteAccount")
    public void deleteAccount(
            HttpServletRequest request
    ) {
        merchantService.deleteAccount(request);
    }
}
