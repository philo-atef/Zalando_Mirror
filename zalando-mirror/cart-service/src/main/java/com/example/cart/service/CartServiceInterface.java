package com.example.cart.service;

import com.example.cart.model.Cart;
import com.shared.dto.cart.CartItemDto;
import com.shared.dto.search.SearchRequest;

import java.util.List;

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
