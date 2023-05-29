package zalando.inventoryservice.controller;

import com.shared.dto.inventory.UnavailableItemResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zalando.inventoryservice.dto.CartItemDto;
import zalando.inventoryservice.dto.CreateItemDto;
import zalando.inventoryservice.dto.UnavailableItemDto;
import zalando.inventoryservice.exceptions.NotFoundException;
import zalando.inventoryservice.exceptions.OutOfStockException;
import zalando.inventoryservice.model.InventoryItem;
import zalando.inventoryservice.service.InventoryService;

import java.util.List;


@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @PostMapping("/")
    public ResponseEntity<?> createInventoryItem(@RequestBody CreateItemDto createItemDto){
        InventoryItem inventoryItem = inventoryService.createInventoryItem(createItemDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(inventoryItem);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<?> getProductInventory(@PathVariable String productId) {
        try {
            List<InventoryItem> inventoryItems = inventoryService.getProductInventory(productId);
            return ResponseEntity.ok(inventoryItems);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/{productId}/instock")
    public ResponseEntity<String> checkIfProductIsInStock(@PathVariable String productId) {
        try {
            boolean isInStock = inventoryService.isInStock(productId);
            if (isInStock) {
                return ResponseEntity.ok("Product with ID " + productId + " is in stock");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product with ID " + productId + " is out of stock");
            }
        }
        catch(NotFoundException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PutMapping("/change-quantity")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> changeQuantityBy(@RequestBody List<CartItemDto> cartItems) {
        try {
            inventoryService.changeQuantityBy(cartItems);
            return new ResponseEntity<>("Quantity changed successfully", HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (OutOfStockException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping("/validate-cart")
    public ResponseEntity<?> validateCart(@RequestBody List<CartItemDto> cartItems) {
        try{
            List<UnavailableItemDto> unavailableItems = inventoryService.validateCartContent(cartItems);
            if (unavailableItems.isEmpty()) {
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(unavailableItems);
            }
        }
        catch(NotFoundException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
