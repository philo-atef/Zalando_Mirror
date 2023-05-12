package com.example.cart.dto;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InventoryItemRequest {
    @Id
    private String id;
    private String brandId;
    private String brandName;
    private String name;
    private Double price;
    private String material;
    private List<String> colors;
    private List<String> sizes;
    private String gender;
    private String category;
    private String subcategory;

}
