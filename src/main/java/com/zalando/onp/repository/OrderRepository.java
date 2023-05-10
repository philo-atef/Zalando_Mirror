package com.zalando.onp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.zalando.onp.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
