package zalando.inventoryservice.service;

import com.shared.dto.inventory.InventoryItemRequest;
import com.shared.dto.inventory.UnavailableItemResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zalando.inventoryservice.dto.CartItemDto;
import zalando.inventoryservice.dto.CreateItemDto;
import zalando.inventoryservice.dto.UnavailableItemDto;
import zalando.inventoryservice.exceptions.NotFoundException;
import zalando.inventoryservice.exceptions.OutOfStockException;
import zalando.inventoryservice.model.InventoryItem;
import zalando.inventoryservice.repository.InventoryRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    private String generateSkuCode(String productId, String color, String size) {
        return productId + "-" + color + "-" + size;
    }

    @Transactional
    public InventoryItem createInventoryItem(CreateItemDto createItemDto){
        InventoryItem inventoryItem = InventoryItem.builder()
                .sku(generateSkuCode(createItemDto.getProductId(), createItemDto.getColor(), createItemDto.getSize()))
                .productId(createItemDto.getProductId())
                .color(createItemDto.getColor())
                .size(createItemDto.getSize())
                .quantity(createItemDto.getQuantity())
                .build();

        return inventoryRepository.save(inventoryItem);
    }

    @Transactional
    public List<InventoryItem> bulkCreateInventoryItem(List<CreateItemDto> createItemDtos){
        List<InventoryItem> toBeCreatedItems = new ArrayList<InventoryItem>();
        for(CreateItemDto item : createItemDtos){
            InventoryItem inventoryItem = InventoryItem.builder()
                    .sku(generateSkuCode(item.getProductId(), item.getColor(), item.getSize()))
                    .productId(item.getProductId())
                    .color(item.getColor())
                    .size(item.getSize())
                    .quantity(item.getQuantity())
                    .build();

            toBeCreatedItems.add(inventoryItem);
        }

        return inventoryRepository.saveAll(toBeCreatedItems);
    }



    @Transactional(readOnly = true)
    public List<InventoryItem> getProductInventory(String productId){
        Optional<List<InventoryItem>> itemsOptional = inventoryRepository.findByProductId(productId);
        if(!itemsOptional.isPresent()){
            throw new NotFoundException("Product with ID " + productId + " not found in inventory");
        }
        return itemsOptional.get();
    }

    @Transactional(readOnly = true)
    public boolean isInStock(String productId){
        Optional<List<InventoryItem>> itemsOptional = inventoryRepository.findByProductId(productId);
        if(!itemsOptional.isPresent()){
            throw new NotFoundException("Product with ID " + productId + " not found in inventory");
        }

        List<InventoryItem> items = itemsOptional.get();
        for(InventoryItem item : items){
            if(item.getQuantity() > 0){
                return false;
            }
        }
        return true;
    }

    @Transactional
    public List<InventoryItem> changeQuantityBy(List<CartItemDto> cartItems) {
        List<InventoryItem> updatedItems = new ArrayList<InventoryItem>();
        for(CartItemDto cartItem : cartItems){
            String skuCode = generateSkuCode(cartItem.getProductId(), cartItem.getColor(), cartItem.getSize());
            int changeAmount = cartItem.getQuantity();

            Optional<InventoryItem> itemOptional = inventoryRepository.findBySkuCode(skuCode);
            if(!itemOptional.isPresent()){
                throw new NotFoundException("Product with sku-code " + skuCode + " not found in inventory");
            }

            InventoryItem item = itemOptional.get();
            if(item.getQuantity() + changeAmount < 0){
                throw new OutOfStockException("Cannot change quantity by " + changeAmount + ", only " + item.getQuantity() + " available");
            }

            item.setQuantity(item.getQuantity() + changeAmount);
            updatedItems.add(item);
        }

        inventoryRepository.saveAll(updatedItems);
        return updatedItems;
    }

    @Transactional
    public List<UnavailableItemDto> validateCartContent(List<CartItemDto> cartItems) {
        List<UnavailableItemDto> unavailableItems = new ArrayList<>();

        for (CartItemDto cartItem : cartItems) {
            String skuCode = generateSkuCode(cartItem.getProductId(), cartItem.getColor(), cartItem.getSize());
            int requestedQuantity = cartItem.getQuantity();

            Optional<InventoryItem> itemOptional = inventoryRepository.findBySkuCode(skuCode);
            if (itemOptional.isPresent()) {
                InventoryItem item = itemOptional.get();
                int availableQuantity = item.getQuantity();
                if (requestedQuantity > availableQuantity) {
                    unavailableItems.add(UnavailableItemDto.builder()
                            .productId(cartItem.getProductId())
                            .color(cartItem.getColor())
                            .size(cartItem.getSize())
                            .availableQuantity(availableQuantity)
                            .requestedQuantity(requestedQuantity)
                            .build());
                }
            } else {
                throw new NotFoundException("Product with sku-code " + skuCode + " not found in inventory");
            }
        }

        return unavailableItems;
    }

}
