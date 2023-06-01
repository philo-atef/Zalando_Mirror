package com.shared.dto.cart;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonSerialize
@JsonDeserialize
public class CartItemDto {
    private String carItemID;
    private String productID;
    private String name;
    private String brandId;
    private String brandName;
    private String color;
    private String size;
    private Integer quantity;
    private Double price;
}
