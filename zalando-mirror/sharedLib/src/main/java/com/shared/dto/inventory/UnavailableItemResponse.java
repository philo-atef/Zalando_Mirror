package com.shared.dto.inventory;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonSerialize
@JsonDeserialize
@Builder
public class UnavailableItemResponse {
    private String productId;
    private String color;
    private String size;
    private int availableQuantity;
    private int requestedQuantity;
}