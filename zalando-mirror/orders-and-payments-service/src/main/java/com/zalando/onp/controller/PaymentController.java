package com.zalando.onp.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zalando.onp.model.Payment;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zalando.onp.exception.ResourceNotFoundException;
import com.zalando.onp.model.Payment;
import com.zalando.onp.repository.PaymentRepository;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1/")
public class PaymentController {

    @Autowired
    private PaymentRepository paymentRepository;

    @GetMapping("/payments")
    public List<Payment> getAllPayments(){
        return paymentRepository.findAll();
    }


    @PostMapping("/payments")
    public Payment createPayment(@Valid @RequestBody Payment payment) {
        return paymentRepository.save(payment);
    }

    @GetMapping("/payment/{id}")
    public ResponseEntity<Payment> getPaymentById(@PathVariable Long id) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Payment does not exist with id :" + id));
        return ResponseEntity.ok(payment);
    }


    @PutMapping("/payments/{id}")
    public ResponseEntity<Payment> updatePayment(@PathVariable Long id, @Valid @RequestBody Payment paymentDetails){
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not exist with id :" + id));

        var curOrderID = payment.getOrder_id();
        if(paymentDetails.getOrder_id()!=null){
            curOrderID = paymentDetails.getOrder_id();
        }
        payment.setOrder_id(curOrderID);


        var curCHN = payment.getCard_holder_name();
        if(paymentDetails.getCard_holder_name()!=null){
            curCHN = paymentDetails.getCard_holder_name();
        }
        payment.setCard_holder_name(curCHN);

        var curCNU = payment.getCard_num_used();
        if(paymentDetails.getCard_num_used()!=null){
            curCNU = paymentDetails.getCard_num_used();
        }
        payment.setCard_num_used(curCNU);


        var curPS = payment.getPayment_status();
        if(paymentDetails.getPayment_status()!=null){
            curPS = paymentDetails.getPayment_status();
        }
        payment.setPayment_status(curPS);


        var curED = payment.getExpiration_date();
        if(paymentDetails.getExpiration_date()!=null){
            curED = paymentDetails.getExpiration_date();
        }
        payment.setExpiration_date(curED);


        var curCVV = payment.getCvv();
        if(paymentDetails.getCvv()!=null){
            curCVV = paymentDetails.getCvv();
        }
        payment.setCvv(curCVV);


        Payment updatedPayment = paymentRepository.save(payment);
        return ResponseEntity.ok(updatedPayment);
    }

    @DeleteMapping("/payments/{id}")
    public ResponseEntity<Map<String, Boolean>> deletePayment(@PathVariable Long id){
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not exist with id :" + id));

        paymentRepository.delete(payment);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }
}