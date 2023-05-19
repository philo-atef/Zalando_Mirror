package net.example.dto;


import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InventoryItem {

    private String sku;


    private String productId;


    private String color;


    private String size;


    private int quantity;
}
