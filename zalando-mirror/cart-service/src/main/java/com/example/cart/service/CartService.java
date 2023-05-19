package com.example.cart.service;

import com.example.cart.dto.*;
import com.example.cart.exception.CartEmptyException;
import com.example.cart.exception.NoSuchElementFoundException;
import com.example.cart.model.Cart;
import com.example.cart.model.CartItem;
import com.example.cart.rabbitmq.publisher.inventoryProducer;
import com.example.cart.rabbitmq.publisher.orderAndPaymentProducer;
import com.example.cart.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.*;

@Service
public class CartService  implements CartServiceInterface{

    private final CartRepository cartRepository;
    private final inventoryProducer inventoryProducer;
    private final orderAndPaymentProducer orderAndPaymentProducer;

    @Autowired
    public CartService(CartRepository cartRepository, inventoryProducer inventoryProducer, orderAndPaymentProducer orderAndPaymentProducer) {
        this.cartRepository = cartRepository;
        this.inventoryProducer = inventoryProducer;
        this.orderAndPaymentProducer = orderAndPaymentProducer;
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

    public List<InventoryItemRequest> formatInventoryRequest(Cart cart) {

        ArrayList<InventoryItemRequest> listOfItems = new ArrayList<>();

        for (CartItem c: cart.getCartItemsList()) {

            InventoryItemRequest item = new InventoryItemRequest();
            item.setColor(c.getColor());
            item.setSize(c.getSize());
            item.setQuantity(c.getQuantity());
            item.setProductID(c.getProductID());

            listOfItems.add(item);

        }

        return listOfItems;
    }

    public OrderRequest formatOrderRequest(Cart cart) {

        ArrayList<CartItemDto> listOfItems = new ArrayList<>();

        for (CartItem c: cart.getCartItemsList()) {

            CartItemDto item = new CartItemDto();
            item.setColor(c.getColor());
            item.setSize(c.getSize());
            item.setQuantity(c.getQuantity());
            item.setProductID(c.getProductID());

            listOfItems.add(item);
        }

        OrderRequest request = new OrderRequest(cart.getId(),cart.getUserID(),cart.getTotalPrice(),listOfItems);

        return request;
    }

    public SearchResponse formatSearchResponse(Cart cart, SearchRequest searchRequest) {

        SearchResponse response = new SearchResponse();

        boolean added = false ;

        for (CartItem c: cart.getCartItemsList()) {

            if(c.getProductID().equals(searchRequest.getProductID())
            && c.getSize().equals(searchRequest.getSize())
            && c.getColor().equals(searchRequest.getColor())
            && c.getQuantity() >= searchRequest.getQuantity())
            {
                added = true ;
                break;
            }

        }

        response.setAdded(added);
        response.setSize(searchRequest.getSize());
        response.setColor(searchRequest.getColor());
        response.setProductID(searchRequest.getProductID());
        response.setQuantity(searchRequest.getQuantity());

        return response;
    }

    @Override
    public List<Cart> getAllCarts() {
        return cartRepository.findAll();
    }

    @Override
    public Cart getUserCartById(UUID userId) {

        //Check user is logged in

        Cart userCart = cartRepository.findCartByUserID(userId);

        return  updateCart(userCart);
    }

    @Override
    public Cart createNewCart(UUID userId) {

        // Check user is logged in

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
    public Cart emptyCart(UUID userId) {

        Cart cart = cartRepository.findCartByUserID(userId);

        if(cart != null)
        {
            cart.setCartItemsList(new ArrayList<>());
            cart.setTotalPrice(0.0);
        }

        return cartRepository.save(cart);
    }

    @Override
    public Cart addCartItem(UUID userId, SearchRequest searchRequest) {

        // Check if the user is logged

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
                .productID(searchRequest.getProductID())
                .price(searchRequest.getPrice())
                .brandName(searchRequest.getBrandName())
                .name(searchRequest.getName())
                .brandId(searchRequest.getBrandId())
                .color(searchRequest.getColor())
                .size(searchRequest.getSize())
                .quantity(searchRequest.getQuantity())
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

        // Check if the user is logged


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

        // Check if the user is logged


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
    public Cart updateCart(Cart cart) {

        List<InventoryItemRequest> items =  formatInventoryRequest(cart);
        InventoryItemsRequest request = new InventoryItemsRequest(items);

        InventoryResponse response = inventoryProducer.sendMessage(request);

        if(response != null && response.getItemsNotFound().isEmpty())
        {
            return cartRepository.save(cart) ;
        }

        ArrayList<CartItem> newList = new ArrayList<>();
        Double total = 0.0 ;

        for (CartItem item: cart.getCartItemsList()) {

            boolean found = false ;

            for (InventoryItem product: response.getItemsNotFound()) {

                // Needs an update
                if(product.getProductId().equals(item.getProductID()) &&
                     product.getSize().equals(item.getSize()) &&
                      product.getColor().equals(item.getColor()))
                {
                    if(product.getQuantity() > 0)
                    {
                        item.setQuantity(product.getQuantity());
                        newList.add(item);

                        total+= item.getPrice() * item.getQuantity() ;
                    }
                }

            }

            if(!found)
            {
                newList.add(item);
                total+= item.getPrice() * item.getQuantity() ;
            }

        }

        cart.setCartItemsList(newList);
        cart.setTotalPrice(total);

        return cartRepository.save(cart) ;
    }

    @Override
    public Cart placeOrder(UUID userID) {
        // Check if the user is logged

        // Get the Cart
        Cart cart = cartRepository.findCartByUserID(userID);

        // Format the message
        OrderRequest request = formatOrderRequest(cart);

        OrderResponse response = orderAndPaymentProducer.sendMessage(request);

        if(response != null)
        {
            if(response.isOrdered())
            {
                return emptyCart(userID);
            }
            else
            {
                 return updateCart(cart);
            }
        }

        return cart;

    }


}
