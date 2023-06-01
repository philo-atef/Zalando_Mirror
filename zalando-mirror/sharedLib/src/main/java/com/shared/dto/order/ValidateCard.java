package com.shared.dto.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ValidateCard {
    private Long order_id;
    private Long payment_id;
    private String card_holder_name;
    private String cvv;
    private String expiration_date;

}
