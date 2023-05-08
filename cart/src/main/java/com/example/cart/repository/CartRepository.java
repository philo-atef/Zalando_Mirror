package com.example.cart.repository;

import com.example.cart.model.Cart;
import com.example.cart.model.CartItem;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface CartRepository extends MongoRepository<Cart, UUID> {
  //  public Cart findCartByUserID(UUID user_id);

}
