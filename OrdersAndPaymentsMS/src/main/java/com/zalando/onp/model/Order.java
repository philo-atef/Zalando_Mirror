package com.zalando.onp.model;

import java.util.*;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.Type;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name= "Orders")
public class Order{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long order_id;
    @Column(name = "customer_id")
    private Long customer_id;
    @Column(name = "order_date")
    private Date order_date;
    @Column(name = "shipping_address")
    private String shipping_address;
    @Column(name = "card_num_used")
    private Long card_num_used;
    @Column(name = "total_amount")
    private double total_amount;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "json", name = "products")
    private List<Product> products = new ArrayList<Product>();
    @Column(name = "order_status")
    private String order_status;

    public Order() {
        super();
    }
    public Order(Long customer_id, Date order_date, String shipping_address, Long card_num_used, double total_amount, List<Product> products, String order_status) {
        this.customer_id = customer_id;
        this.order_date = order_date;
        this.shipping_address = shipping_address;
        this.card_num_used = card_num_used;
        this.total_amount = total_amount;
        this.products = products;
        this.order_status = order_status;
    }


    public Long getOrder_id() {
        return order_id;
    }

    public void setOrder_id(Long order_id) {
        this.order_id = order_id;
    }

    public Long getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(Long customer_id) {
        this.customer_id = customer_id;
    }

    public Date getOrder_date() {
        return order_date;
    }

    public void setOrder_date(Date order_date) {
        this.order_date = order_date;
    }

    public String getShipping_address() {
        return shipping_address;
    }

    public void setShipping_address(String shipping_address) {
        this.shipping_address = shipping_address;
    }

    public Long getCard_num_used() {
        return card_num_used;
    }

    public void setCard_num_used(Long card_num_used) {
        this.card_num_used = card_num_used;
    }

    public double getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(double total_amount) {
        this.total_amount = total_amount;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public String getOrder_status() {
        return order_status;
    }

    public void setOrder_status(String order_status) {
        this.order_status = order_status;
    }
}