package com.shared.dto.search;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SearchResponse {
    private String productID;
    private String color;
    private String size;
    private Integer quantity;
    private boolean added;
}
