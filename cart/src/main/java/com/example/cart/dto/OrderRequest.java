package com.example.cart.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {
    private List<CartItemDto> cartItemsDtoList;
}
