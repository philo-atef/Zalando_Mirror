package com.example.cart.model;

import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(value = "cart")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Cart {

    @Id
    private UUID id;
    private UUID user_id;
    private Double totalPrice;
    private List<CartItem> cartItemsList;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getUser_id() {
        return user_id;
    }

    public void setUser_id(UUID user_id) {
        this.user_id = user_id;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public List<CartItem> getOrderLineItemsList() {
        return cartItemsList;
    }

    public void setOrderLineItemsList(List<CartItem> orderLineItemsList) {
        this.cartItemsList = orderLineItemsList;
    }
}
