package com.example.cart.service;

import com.example.cart.dto.CartItemDto;
import com.example.cart.model.Cart;
import com.example.cart.model.CartItem;

import java.util.UUID;

public class CartService  implements CartServiceInterface{


    private CartItem mapToDto(CartItemDto cartItemDto) {
        CartItem cartItem = new CartItem();
        cartItem.setPrice(cartItemDto.getPrice());
        cartItem.setQuantity(cartItemDto.getQuantity());
        cartItem.setColor(cartItemDto.getColor());
        cartItem.setDescription(cartItemDto.getDescription());
        cartItem.setProductName(cartItemDto.getProductName());
        cartItem.setSize(cartItemDto.getSize());
        cartItem.setCart(cartItemDto.getCart());
        cartItem.setCarItem_id(cartItemDto.getCarItem_id());
        cartItem.setProduct_id(cartItemDto.getProduct_id());
        return cartItem;
    }

    @Override
    public Cart getUserCartById(UUID userId) {
        return null;
    }

    @Override
    public UUID createNewCart(UUID userId) {
        return null;
    }

    @Override
    public boolean emptyCart(UUID userId) {
        return false;
    }

    @Override
    public UUID addCartItem(UUID userId) {
        return null;
    }

    @Override
    public Cart removeCartItem(UUID userId, CartItem cartItem) {
        return null;
    }

    @Override
    public Cart editCartItem(UUID userId, UUID cartItemId, CartItem edit) {
        return null;
    }
}
