package com.example.cart.service;

import com.example.cart.dto.CartItemDto;
import com.example.cart.dto.OrderResponse;
import com.example.cart.dto.ProductRequest;
import com.example.cart.model.Cart;
import com.example.cart.model.CartItem;

import java.util.UUID;

public interface CartServiceInterface {

    Cart getUserCartById (UUID userId);
    Cart createNewCart(UUID userId);
    boolean emptyCart(OrderResponse orderResponse);
    Cart addCartItem(UUID userId, ProductRequest productRequest);
    Cart removeCartItem(UUID userId, CartItemDto cartItemDto);
    Cart editCartItem(UUID userId, CartItemDto cartItemDto);
}
