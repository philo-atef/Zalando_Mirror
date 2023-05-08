package com.example.cart.service;

import com.example.cart.model.Cart;
import com.example.cart.model.CartItem;

import java.util.UUID;

public interface CartServiceInterface {

    Cart getUserCartById (UUID userId);
    UUID createNewCart(UUID userId);
    boolean emptyCart(UUID userId);
    UUID addCartItem(UUID userId);
    Cart removeCartItem(UUID userId, CartItem cartItem);
    Cart editCartItem(UUID userId, UUID cartItemId, CartItem edit);
}
