package net.example.Product;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Arrays;
import java.util.List;

@Document(collection = "product")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

@CompoundIndexes({
        @CompoundIndex(name = "search_index", def = "{ 'brandName': 'text', 'name': 'text' ,'material': 'text' ,'colors': 'text' ,'gender': 'text' ,'category': 'text' ,'subcategory': 'text' ,'sizes': 'text' }")
})
public class Product {
    @Id
    private String id;
    @NotNull
    private String brandId;
    @NotNull
    private String brandName;
    @NotNull
    private String name;
    @NotNull
    private Double price;
    @NotNull
    private String material;
    @NotNull
    private List<String> colors;
    @NotNull
    private List<String> sizes;
    @NotNull
    private String gender;
    @NotNull
    private String category;
    @NotNull
    private String subcategory;


    // Validation rules
    @AssertTrue(message = "Invalid Gender: Gender can only be [Men's - Women's - Unisex]")
    private boolean isValidGender(){
        return Arrays.asList("Men's", "Women's", "Unisex").contains(gender);
    }

    @AssertTrue(message = "Invalid subcategory for the given category")
    private boolean isValidSubcategory() {
        List<String> VALID_CLOTHING_SUBCATEGORIES = Arrays.asList(
                "T-shirts & Polos",
                "Shirts",
                "Sweatshirts & Hoodies",
                "Trousers",
                "Jeans",
                "Shorts",
                "Jackets",
                "Knitwear",
                "Sportswear",
                "Tracksuits & Joggers",
                "Suits & Tailoring",
                "Coats",
                "Underwear & Socks",
                "Swimwear",
                "Loungewear & Sleepwear"
        );
        List<String> VALID_SHOES_SUBCATEGORIES = Arrays.asList(
                "Sneakers",
                "Open shoes",
                "Lace-up shoes",
                "Loafers",
                "Business shoes",
                "Boots",
                "Sports shoes",
                "Outdoor shoes",
                "Slippers",
                "Shoe accessories"
        );
        List<String> VALID_ACCESSORIES_SUBCATEGORIES = Arrays.asList(
                "Accessories",
                "Bags & cases",
                "Beanies, hats & caps",
                "Sunglasses",
                "Jewellery",
                "Watches",
                "Belts",
                "Wallets & card holders",
                "Blue-light glasses",
                "Ties & accessories",
                "Scarves & Shawls",
                "Gloves",
                "Lifestyle Electronics",
                "Miscellaneous",
                "Umbrellas",
                "Masks"
        );

        if (category.equalsIgnoreCase("Clothing")) {
            return VALID_CLOTHING_SUBCATEGORIES.contains(subcategory);
        } else if (category.equalsIgnoreCase("Shoes")) {
            return VALID_SHOES_SUBCATEGORIES.contains(subcategory);
        } else if (category.equalsIgnoreCase("Accessories")) {
            return VALID_ACCESSORIES_SUBCATEGORIES.contains(subcategory);
        }
        return false;
    }
}
