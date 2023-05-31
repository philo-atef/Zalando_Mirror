package com.zalando.onp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderRequest {
    private Cart cart;
    private String shipAdd ;
    private String creditNum;
//    private String cardHolder;
//    private String cvv;
//    private String expDate;
}
