package zalando.inventoryservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateItemDto {
    private String productId;
    private String color;
    private String size;
    private int quantity;
    private String brandName;
}
