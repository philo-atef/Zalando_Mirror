package com.example.cart.dto;

import com.example.cart.model.Cart;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartItemDto {
    private UUID carItemID;
    private UUID productID;
    private String name;
    private String brandId;
    private String brandName;
    private String color;
    private String size;
    private Integer quantity;
    private Double price;
}
