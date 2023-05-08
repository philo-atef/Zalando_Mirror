package com.example.cart.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {
    private UUID productID;
    private Double price;
    private String productName;
    private String description;
    private String color;
    private String size;
    private Integer quantity;
}
