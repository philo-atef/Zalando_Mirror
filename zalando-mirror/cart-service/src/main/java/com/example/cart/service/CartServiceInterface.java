package com.example.cart.service;

import com.example.cart.dto.*;
import com.example.cart.model.Cart;

import java.util.List;
import java.util.UUID;

public interface CartServiceInterface {

    Cart getUserCartById (UUID userId);
    List<Cart> getAllCarts();
    Cart createNewCart(UUID userId);
    Cart emptyCart(UUID userId);
    Cart addCartItem(UUID userId, SearchRequest searchRequest);
    Cart removeCartItem(UUID userId, UUID cartItemID) ;
    Cart editCartItem(UUID userId, CartItemDto cartItemDto);
    Cart updateCart(Cart cart) ;

    Cart placeOrder (UUID userID);
}
