package com.zalando.onp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartItem {

    private String productID;
    private String name;
    private String brandId;
    private String brandName;
    private String color;
    private String size;
    private Integer quantity;
    private Double price;
}
