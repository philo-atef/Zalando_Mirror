package com.zalando.onp.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
import com.zalando.onp.model.Order;
import com.zalando.onp.repository.OrderRepository;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1/")
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    // get all orders
    @GetMapping("/orders")
    public List<Order> getAllOrders(){
        return orderRepository.findAll();
    }

    // create order rest api
    @PostMapping("/orders")
    public Order createOrder(@Valid @RequestBody Order order) {
        return orderRepository.save(order);
    }

    // get order by id rest api
    @GetMapping("/orders/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order does not exist with id :" + id));
        return ResponseEntity.ok(order);
    }

    // update order rest api

    @PutMapping("/orders/{id}")
    public ResponseEntity<Order> updateOrder(@PathVariable Long id, @Valid @RequestBody Order orderDetails){
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not exist with id :" + id));

        var current_customer_id = order.getCustomer_id();
        if(orderDetails.getCustomer_id()!=null){
            current_customer_id = orderDetails.getCustomer_id();
        }
        order.setCustomer_id(current_customer_id);

        var current_Order_date = order.getOrder_date();
        if(orderDetails.getOrder_date()!=null){
            current_Order_date = orderDetails.getOrder_date();
        }
        order.setOrder_date(current_Order_date);

        var current_ship_add = order.getShipping_address();
        if(orderDetails.getShipping_address()!=null){
            current_ship_add = orderDetails.getShipping_address();
        }
        order.setShipping_address(current_ship_add);

        var current_Card_num = order.getCard_num_used();
        if(orderDetails.getCard_num_used()!=null){
            current_Card_num = orderDetails.getCard_num_used();
        }
        order.setCard_num_used(current_Card_num);

        var current_Tot_amt = order.getTotal_amount();
        if(orderDetails.getTotal_amount()!=0){
            current_Tot_amt = orderDetails.getTotal_amount();
        }
        order.setTotal_amount(current_Tot_amt);

        var current_Products = order.getProducts();
        if(orderDetails.getProducts()!=null){
            current_Products = orderDetails.getProducts();
        }
        order.setProducts(current_Products);



        var current_Order_status = order.getOrder_status();
        if(orderDetails.getOrder_status()!=null){
            current_Order_status = orderDetails.getOrder_status();
        }
        order.setOrder_status(current_Order_status);


        Order updatedOrder = orderRepository.save(order);
        return ResponseEntity.ok(updatedOrder);

    }

    // delete order rest api
    @DeleteMapping("/orders/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteOrder(@PathVariable Long id){
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not exist with id :" + id));

        orderRepository.delete(order);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }
}