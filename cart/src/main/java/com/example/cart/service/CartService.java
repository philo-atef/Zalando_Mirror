package com.example.cart.service;

import com.example.cart.dto.*;
import com.example.cart.model.Cart;
import com.example.cart.model.CartItem;
import com.example.cart.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CartService  implements CartServiceInterface{

    private final CartRepository cartRepository;

    @Autowired
    public CartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    private CartItem mapToDto(CartItemDto cartItemDto) {
        CartItem cartItem = new CartItem();
        cartItem.setPrice(cartItemDto.getPrice());
        cartItem.setQuantity(cartItemDto.getQuantity());
        cartItem.setColor(cartItemDto.getColor());
        cartItem.setDescription(cartItemDto.getDescription());
        cartItem.setProductName(cartItemDto.getProductName());
        cartItem.setSize(cartItemDto.getSize());
        cartItem.setCart(cartItemDto.getCart());
        cartItem.setCarItemID(cartItemDto.getCarItemID());
        cartItem.setProductID(cartItemDto.getProductID());
        return cartItem;
    }

    @Override
    public Cart getUserCartById(UUID userId) {
        return cartRepository.findCartByUserID(userId);
    }

    @Override
    public Cart createNewCart(UUID userId) {
        Cart cart = cartRepository.findCartByUserID(userId);

        if(cart == null)
        {
            // No cart instance for the user
            // create a cart instance first
            cart = Cart.builder()
                    .id(UUID.randomUUID())
                    .userID(userId)
                    .totalPrice(0.0)
                    .cartItemsList(new ArrayList<CartItem>())
                    .build();

        }

        return cartRepository.save(cart);
    }

    @Override
    public boolean emptyCart(OrderResponse orderResponse) {
        if(orderResponse.isOrdered())
        {
            // Empty the users' cart
            Cart cart = cartRepository.findCartByUserID(orderResponse.getUserID());

            if(cart == null)
            {
                // No cart instance for the user
               System.out.println("Error: Such case can't happen");
            }
            else
            {
                // Update it to contains nothing
                cart.setCartItemsList(new ArrayList<>());
                cart.setTotalPrice(0.0);
                cartRepository.save(cart);

                System.out.println(cart);
            }

            return true;
        }
        else {

            return false;

        }
    }

    @Override
    public Cart addCartItem(UUID userId, ProductRequest productRequest) {

        Cart cart = cartRepository.findCartByUserID(userId);

        if(cart == null)
        {
            // No cart instance for the user
            // create a cart instance first
             cart = Cart.builder()
                    .id(UUID.randomUUID())
                    .userID(userId)
                    .totalPrice(0.0)
                    .cartItemsList(new ArrayList<CartItem>())
                    .build();
        }

        CartItem cartItem = CartItem.builder()
                .carItemID(UUID.randomUUID())
                .productID(productRequest.getProductID())
                .price(productRequest.getPrice())
                .productName(productRequest.getProductName())
                .description(productRequest.getDescription())
                .color(productRequest.getColor())
                .size(productRequest.getSize())
                .quantity(productRequest.getQuantity())
                .build();

        boolean found = false ;
        if(!cart.getCartItemsList().isEmpty())
        {
            for (CartItem item : cart.getCartItemsList()) {
                if(item.getProductID().equals(cartItem.getProductID()))
                {
                    item.setQuantity(item.getQuantity()+cartItem.getQuantity());
                    found = true;
                }
            }
        }

        if(!found)
        {
            cart.getCartItemsList().add(cartItem);
        }

        cart.setTotalPrice(cart.getTotalPrice()+(cartItem.getQuantity() * cartItem.getPrice()));

        return cartRepository.save(cart);
    }

    @Override
    public Cart removeCartItem(UUID userId, CartItemId cartItemID) {
        Cart cart = cartRepository.findCartByUserID(userId);

        if(cart == null)
        {
            // No cart instance for the user
            System.out.println("Such Error Can't happen !!!");
        }

        int i = 0 ;
        int toBeRemoved = -1 ;

        if(!cart.getCartItemsList().isEmpty())
        {
            for (CartItem item : cart.getCartItemsList()) {
                if(item.getProductID().equals(cartItemID.getCartItemId()))
                {
                    toBeRemoved = i ;
                    cart.setTotalPrice(cart.getTotalPrice() - (item.getPrice() * item.getQuantity()) );
                    break;
                }

                i++;
            }

            if(toBeRemoved != -1)
            {
                cart.getCartItemsList().remove(toBeRemoved);
            }

        }

        return cartRepository.save(cart);
    }
    @Override
    public Cart editCartItem(UUID userId, CartItemDto cartItemDto) {
        Cart cart = cartRepository.findCartByUserID(userId);

        if(cart == null)
        {
            // No cart instance for the user
            System.out.println("Such Error Can't happen !!!");
        }

        CartItem cartItem = mapToDto(cartItemDto);

        int i = 0 ;
        int toBeRemoved = -1 ;

        if(!cart.getCartItemsList().isEmpty())
        {
            for (CartItem item : cart.getCartItemsList()) {
                if(item.getProductID().equals(cartItem.getProductID()))
                {
                    cart.setTotalPrice(cart.getTotalPrice()+(cartItem.getQuantity() * cartItem.getPrice()));
                    cart.setTotalPrice(cart.getTotalPrice()-(item.getQuantity() * item.getPrice()));
                    toBeRemoved = i ;
                    break;
                }

                i++;
            }
            cart.getCartItemsList().remove(toBeRemoved);
            cart.getCartItemsList().add(toBeRemoved,cartItem);
        }

        return cartRepository.save(cart);
    }
}
