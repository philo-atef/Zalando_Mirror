package com.example.cart.model;

import java.util.List;
import java.util.UUID;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Encrypted;


@Document(value = "cart")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Cart {

    @Id
    private String id;
    private String userID;
    private Double totalPrice;
    private List<CartItem> cartItemsList;

    public String toString()
    {
        StringBuilder cartString = new StringBuilder("");
        cartString.append("ID: "+ id +"\n");
        cartString.append("User ID: "+ userID +"\n");
        cartString.append("Total Price: "+ totalPrice +"\n");
        cartString.append("Cart Items: "+ cartItemsList +"\n");

        return cartString.toString();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public List<CartItem> getCartItemsList() {
        return cartItemsList;
    }

    public void setCartItemsList(List<CartItem> cartItemsList) {
        this.cartItemsList = cartItemsList;
    }
}
