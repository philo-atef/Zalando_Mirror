package com.shared.dto.search;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@JsonSerialize
@JsonDeserialize
@AllArgsConstructor
@NoArgsConstructor
public class SearchRequest {
    private String productID;
    private String name;
    private String brandId;
    private String brandName;
    private String color;
    private String size;
    private Integer quantity;
    private Double price;
}