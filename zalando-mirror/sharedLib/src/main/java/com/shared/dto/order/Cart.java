package com.shared.dto.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Cart {
    private String userID;
    private List<CartItem> cartItemsList;
    private String id;
    private double totalPrice;
}
