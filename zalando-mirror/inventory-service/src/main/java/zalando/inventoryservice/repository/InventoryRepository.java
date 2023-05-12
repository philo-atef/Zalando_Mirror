package zalando.inventoryservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import zalando.inventoryservice.model.InventoryItem;

import java.util.List;
import java.util.Optional;

@Repository
public interface InventoryRepository extends JpaRepository<InventoryItem, String> {

    @Query("SELECT i FROM InventoryItem i WHERE i.sku = :skuCode")
    Optional<InventoryItem> findBySkuCode(String skuCode);

    @Query("SELECT i FROM InventoryItem i WHERE i.productId = :productId")
    Optional<List<InventoryItem>> findByProductId(String productId);
}

