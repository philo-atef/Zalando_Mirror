package com.zalando.onp.controller;


//import com.zalando.onp.dto.ValidateCard;
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
//@RequestMapping("/api/v1/")
@RequestMapping("/api/cards")
public class CardController {

    @Autowired
    private CardRepository cardRepository;

//    @Autowired
//    private OrderController orderController;

    @Autowired
    private PaymentController paymentController;

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

//    @PostMapping("/validate")
//    public ResponseEntity<Map<String, Boolean>> checkPaymentValidity(@RequestBody ValidateCard request){
//        String credit = request.getCredit();
//        Double amount = request.getTotal_amount();
//        Long order_id = request.getOrder_id();
//        Long payment_id = request.getPayment_id();
//
//        Card card = cardRepository.findByCredit(credit);
//        Map<String, Boolean> response = new HashMap<>();
//
//        if (amount <= card.getBalance()){ //COMPLETE ORDER
//            response.put("valid", Boolean.TRUE);
//            orderController.confirmOrder(order_id);
//            paymentController.confirmPayment(payment_id);
//
//            //decrement inventory?
//
//            return ResponseEntity.ok(response);
//        }
//
//        response.put("valid", Boolean.FALSE);
//        return ResponseEntity.ok(response);
//    }

    public boolean checkPaymentValidity(String credit, Double amount, Long order_id, Long payment_id){
        Card card = cardRepository.findByCredit(credit);

        if (amount <= card.getBalance()){ //COMPLETE ORDER
            return true;
        }
        return false;
    }


    public void deductOrderAmount(String credit , Double order_total) {
        Card card = cardRepository.findByCredit(credit);
        Double updated_balance = card.getBalance() - order_total;
        card.setBalance(updated_balance);
        Card updatedCard = cardRepository.save(card);
    }
}
