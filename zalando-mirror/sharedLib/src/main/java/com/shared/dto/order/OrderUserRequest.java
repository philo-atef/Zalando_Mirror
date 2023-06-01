package com.shared.dto.order;

import com.shared.dto.order.Cart;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderUserRequest {
    private Cart cart;
    private String shipAdd ;
    private String creditNum;
//    private String cardHolder;
//    private String cvv;
//    private String expDate;
}
