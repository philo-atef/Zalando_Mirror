package com.example.demo.merchant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MerchantEditRequest {
    private String email;
    private String password;
    private String brandName;
    private String hotline;
}
