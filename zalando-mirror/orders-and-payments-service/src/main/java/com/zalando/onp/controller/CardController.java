package com.zalando.onp.controller;


import com.zalando.onp.dto.ValidateCard;
import com.zalando.onp.exception.ResourceNotFoundException;
import com.zalando.onp.model.Card;
import com.zalando.onp.model.Order;
import com.zalando.onp.repository.CardRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1/")
public class CardController {

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private OrderController orderController;

    @PostMapping("/addCard")
    public Card addCard(@Valid @RequestBody Card card) {
        return cardRepository.save(card);
    }

    @DeleteMapping("/deleteCard/{credit}")
    public ResponseEntity<Map<String, Boolean>> deleteCard(@PathVariable String credit){
        Card card = cardRepository.findByCredit(credit);
        cardRepository.delete(card);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/cards/validate")
    private ResponseEntity<Map<String, Boolean>> checkPaymentValidity(@RequestBody ValidateCard request){
        String credit = request.getCredit();
        Double amount = request.getTotal_amount();
        Long order_id = request.getOrder_id();

        Card card = cardRepository.findByCredit(credit);
        Map<String, Boolean> response = new HashMap<>();

        if (amount <= card.getBalance()){
            response.put("valid", Boolean.TRUE);
            orderController.confirmOrder(order_id);
            return ResponseEntity.ok(response);
        }

        response.put("valid", Boolean.FALSE);
        return ResponseEntity.ok(response);
    }

}
