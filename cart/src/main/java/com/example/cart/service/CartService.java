package com.example.cart.service;

import com.example.cart.dto.*;
import com.example.cart.exception.CartEmptyException;
import com.example.cart.exception.NoSuchElementFoundException;
import com.example.cart.model.Cart;
import com.example.cart.model.CartItem;
import com.example.cart.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.*;

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
        cartItem.setBrandId(cartItemDto.getBrandId());
        cartItem.setBrandName(cartItemDto.getBrandName());
        cartItem.setSize(cartItemDto.getSize());
        cartItem.setName(cartItemDto.getName());
        cartItem.setCarItemID(cartItemDto.getCarItemID());
        cartItem.setProductID(cartItemDto.getProductID());
        return cartItem;
    }

    @Override
    public List<Cart> getAllCarts() {
        return cartRepository.findAll();
    }

    @Override
    public Cart getUserCartById(UUID userId) {
        // To Be Modified as follows:-
        // Send a Request to the inventory or product service with the cart elements
        // Receive the inventory elements
        // call the updateCart method tho update cart
        // return such updated cart
        // The current implementation is simply retrieving the cart without any checking with the inventory
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
    public void emptyCart(UUID userId) {

        Cart cart = cartRepository.findCartByUserID(userId);

        if(cart != null)
        {
            cart.setCartItemsList(new ArrayList<>());
            cart.setTotalPrice(0.0);
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
                .brandName(productRequest.getBrandName())
                .name(productRequest.getName())
                .brandId(productRequest.getBrandId())
                .color(productRequest.getColor())
                .size(productRequest.getSize())
                .quantity(productRequest.getQuantity())
                .build();

        boolean found = false ;
        if(!cart.getCartItemsList().isEmpty())
        {
            for (CartItem item : cart.getCartItemsList()) {
                if(item.getProductID().equals(cartItem.getProductID()) &&
                        item.getColor().equals(cartItem.getColor()) &&
                        item.getSize().equals(cartItem.getSize()))
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
    public Cart removeCartItem(UUID userId, UUID cartItemID) {
        Cart cart = cartRepository.findCartByUserID(userId);

        if(cart == null)
        {
            // No cart instance for the user
            throw new NoSuchElementFoundException("No cart instance instantiated for such user !!");
        }

        boolean found = false;

        ArrayList<CartItem> newList = new ArrayList<>();

        if(!cart.getCartItemsList().isEmpty())
        {
            for (CartItem item : cart.getCartItemsList()) {
                if(item.getCarItemID().equals(cartItemID))
                {
                    found = true;
                    cart.setTotalPrice(cart.getTotalPrice() - (item.getPrice() * item.getQuantity()) );
                }
                else
                {
                    newList.add(item);
                }

            }

            if(found)
            {
                cart.setCartItemsList(newList);
            }
            else
            {
                throw new NoSuchElementFoundException("Cart Item not found !!");
            }

        }
        else
        {
            throw new CartEmptyException("Cart Item is empty !!");
        }


        return cartRepository.save(cart);
    }

    @Override
    public Cart editCartItem(UUID userId, CartItemDto cartItemDto) {
        Cart cart = cartRepository.findCartByUserID(userId);

        if(cart == null)
        {
            // No cart instance for the user
            throw new NoSuchElementFoundException("No cart instance instantiated for such user !!");
        }

        CartItem cartItem = mapToDto(cartItemDto);

        int i = 0 ;
        int toBeEdited = -1 ;

        if(!cart.getCartItemsList().isEmpty())
        {
            for (CartItem item : cart.getCartItemsList()) {
                if(item.getProductID().equals(cartItem.getProductID())&&
                        item.getColor().equals(cartItem.getColor()) &&
                        item.getSize().equals(cartItem.getSize()) )
                {
                    cart.setTotalPrice(cart.getTotalPrice()-(item.getQuantity() * item.getPrice()));
                    cart.setTotalPrice(cart.getTotalPrice()+(cartItem.getQuantity() * cartItem.getPrice()));
                    toBeEdited = i ;
                    break;
                }

                i++;
            }

            if(toBeEdited != -1)
            {
                cart.getCartItemsList().remove(toBeEdited);
                cart.getCartItemsList().add(toBeEdited,cartItem);
            }
            else
            {
                throw new NoSuchElementFoundException("Cart Item not found !!");
            }
        }
        else
        {
            throw new CartEmptyException("Cart Item is empty !!");
        }

        return cartRepository.save(cart);
    }

    @Override
    public void updateCart(UUID userId, InventoryItemsRequest inventoryItemsRequest) {
        // Get The Cart
        // Loop over the elements from the inventory or product service
        // update the cart accordingly
    }


}
