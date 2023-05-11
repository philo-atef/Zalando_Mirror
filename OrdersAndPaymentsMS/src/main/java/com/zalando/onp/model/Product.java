package com.zalando.onp.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
//import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

//@Document(collection = "product")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {
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
