package com.zalando.onp.model;

import java.util.*;

import com.zalando.onp.dto.CartItem;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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
    @Size(max = 100, message = "Address must not exceed 100 characters")
    @Pattern(regexp = "[a-zA-Z0-9\\s]+", message = "Address must contain only alphanumeric characters and spaces")
    @Column(name = "shipping_address")
    private String shipping_address;
    @Pattern(regexp = "\\d{16}", message = "Card number used must be a 16-digit number")
    @Column(name = "card_num_used")
    private String card_num_used;
    @DecimalMin(value = "0.01", message = "Price must be greater than or equal to 0.01")
    @DecimalMax(value = "9999.99", message = "Price must be less than or equal to 9999.99")
    @Column(name = "total_amount")
    private double total_amount;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "json", name = "products")
    private List<CartItem> products = new ArrayList<CartItem>();
    @Pattern(regexp = "^[A-Za-z ]+$", message = "Order status should only contain alphabetic characters")
    @Column(name = "order_status")
    private String order_status;

    public Order() {
        super();
    }
    public Order(Long customer_id, Date order_date, String shipping_address, String card_num_used, double total_amount, List<CartItem> products, String order_status) {
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

    public String getCard_num_used() {
        return card_num_used;
    }

    public void setCard_num_used(String card_num_used) {
        this.card_num_used = card_num_used;
    }

    public double getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(double total_amount) {
        this.total_amount = total_amount;
    }

    public List<CartItem> getProducts() {
        return products;
    }

    public void setProducts(List<CartItem> products) {
        this.products = products;
    }

    public String getOrder_status() {
        return order_status;
    }

    public void setOrder_status(String order_status) {
        this.order_status = order_status;
    }
}