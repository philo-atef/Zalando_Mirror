package net.example.Product;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "product")
@Data
@NoArgsConstructor
@AllArgsConstructor


@Builder
@CompoundIndexes({
        @CompoundIndex(name = "search_index", def = "{ 'brandName': 'text', 'name': 'text' ,'material': 'text' ,'colors': 'text' ,'gender': 'text' ,'category': 'text' ,'subcategory': 'text' ,'sizes': 'text' }")
})
public class Product {
    @Id
    @Setter @Getter  private String id;
    @Setter @Getter private String brandId;
    @Setter @Getter private String brandName;
    @Setter @Getter private String name;
    @Setter @Getter private Double price;
    @Setter @Getter  private String material;
    @Setter @Getter private List<String> colors;
    @Setter @Getter  private List<String> sizes;
    @Setter @Getter  private String gender;
    @Setter @Getter  private String category;
    @Setter @Getter private String subcategory;

    @Override
    public String toString() {
        return "Product{" +
                "id='" + id + '\'' +
                ", brandId='" + brandId + '\'' +
                ", brandName='" + brandName + '\'' +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", material='" + material + '\'' +
                ", colors=" + colors +
                ", sizes=" + sizes +
                ", gender='" + gender + '\'' +
                ", category='" + category + '\'' +
                ", subcategory='" + subcategory + '\'' +
                '}';
    }
}
