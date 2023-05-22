package com.example.cart.service;

import com.example.cart.dto.*;
import com.example.cart.model.Cart;

import java.util.List;
import java.util.UUID;

public interface CartServiceInterface {

    Cart getUserCartById (String userId);
    List<Cart> getAllCarts();
    Cart createNewCart(String userId);
    Cart emptyCart(String userId);
    Cart addCartItem(String userId, SearchRequest searchRequest);
    Cart removeCartItem(String userId, String cartItemID) ;
    Cart editCartItem(String userId, CartItemDto cartItemDto);
    Cart updateCart(Cart cart) ;

    Cart placeOrder (String userID);
}
