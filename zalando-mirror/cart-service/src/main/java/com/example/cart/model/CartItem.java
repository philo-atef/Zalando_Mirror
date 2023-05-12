package com.example.cart.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Document(value = "cartItem")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class CartItem {
    @Id
    private UUID carItemID;
    private UUID productID;
    private String name;
    private String brandId;
    private String brandName;
    private String color;
    private String size;
    private Integer quantity;
    private Double price;


    public String toString()
    {
        StringBuilder cartItemString = new StringBuilder("");
        cartItemString.append("ID: "+ carItemID +"\n");
        cartItemString.append("Product ID: "+ productID +"\n");
        cartItemString.append("Product Name: "+ name +"\n");
        cartItemString.append("Price: "+ price +"\n");
        cartItemString.append("Brand Name: "+ brandName +"\n");
        cartItemString.append("Color: "+ color +"\n");
        cartItemString.append("Size: "+ size +"\n");
        cartItemString.append("Quantity: "+ quantity +"\n");

        return cartItemString.toString();
    }

    public UUID getCarItemID() {
        return carItemID;
    }

    public void setCarItemID(UUID carItemID) {
        this.carItemID = carItemID;
    }

    public UUID getProductID() {
        return productID;
    }

    public void setProductID(UUID productID) {
        this.productID = productID;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrandId() {
        return brandId;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}