package com.shared.dto.order;

import com.shared.dto.cart.CartItemDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {
    private String id;
    private String userID;
    private Double totalPrice;
    private List<CartItemDto> cartItemsList;
}