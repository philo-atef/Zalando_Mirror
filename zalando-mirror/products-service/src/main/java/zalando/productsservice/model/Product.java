package zalando.productsservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
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
        @CompoundIndex(name = "search_index", def = "{ 'brandName': 'text', 'name': 'text' ,'material': 'text' ,'colors': 'text' ,'gender': 'text' ,'category': 'text' ,'subcategory': 'text' ,'sizes': 'text' }")
})
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
