package zalando.inventoryservice.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InventoryItem {
    @Id
    @Column(name = "sku")
    private String sku;

    @Column(name = "productId")
    private String productId;

    @Column(name = "color")
    private String color;

    @Column(name = "size")
    private String size;

    @Column(name = "quantity")
    private int quantity;
}
