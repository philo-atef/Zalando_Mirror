package com.example.cart.controller;


import com.example.cart.dto.CartItemDto;
import com.example.cart.dto.OrderResponse;
import com.example.cart.dto.ProductRequest;
import com.example.cart.model.Cart;
import com.example.cart.model.CartItem;
import com.example.cart.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/carts")
public class CartController {
    private final CartService cartService;
    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PutMapping("/addItem/{userId}")
    public Cart addNewItem(@PathVariable UUID userId, ProductRequest product) {
        return cartService.addCartItem(userId, product);
    }

    @PostMapping("/createCart/{userId}")
    public Cart createCart(@PathVariable UUID userId) {
        return cartService.createNewCart(userId);
    }

    @PostMapping("/orderPlaced")
    public boolean orderPlace(OrderResponse orderResponse) {
        return cartService.emptyCart(orderResponse);
    }

    @PutMapping("/editCartItem/{userId}")
    public Cart editItem(@PathVariable UUID userId, CartItemDto cartItemDto) {
        return cartService.editCartItem(userId,cartItemDto);
    }

    @GetMapping("/{userId}")
    public Cart getUserCart(@PathVariable UUID userId) {
        return cartService.getUserCartById(userId);
    }
}
