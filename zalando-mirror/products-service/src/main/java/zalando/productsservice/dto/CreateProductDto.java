package zalando.productsservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateProductDto {

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

    private MultipartFile file;

    private List<ProductInventoryDto> inventory;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ProductInventoryDto {
        private String productId;
        private String color;
        private String size;
        private int quantity;
    }
}
