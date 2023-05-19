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
public class SearchResponse {
    private UUID productID;
    private String color;
    private String size;
    private Integer quantity;
    private boolean added;
}
