package com.example.cart.dto;

import com.example.cart.model.Cart;
import com.example.cart.model.CartItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartRequest {
    private UUID id;
    private UUID userID;
    private Double totalPrice;
    private List<CartItem> cartItemsList;
}