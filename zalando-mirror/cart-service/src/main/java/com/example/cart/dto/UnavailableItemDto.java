package com.example.cart.dto;


import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UnavailableItemDto {
    private String productId;
    private String color;
    private String size;
    private int availableQuantity;
    private int requestedQuantity;
}