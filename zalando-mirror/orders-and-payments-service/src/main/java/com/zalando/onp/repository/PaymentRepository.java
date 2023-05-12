package com.zalando.onp.repository;

import com.zalando.onp.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PaymentRepository extends JpaRepository<Payment, Long>{

}