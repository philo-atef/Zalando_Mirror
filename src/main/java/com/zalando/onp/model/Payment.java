package com.zalando.onp.model;

import jakarta.persistence.*;
import java.util.*;

@Entity
@Table(name = "Payments")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long payment_id;

    @Column(name = "order_id")
    private Long order_id;

    @Column(name = "card_holder_name")
    private String card_holder_name;

    @Column(name = "card_num_used")
    private Long card_num_used;

    @Column(name = "payment_status")
    private String payment_status;

    @Column(name = "expiration_date")
    private Date expiration_date;

    @Column(name = "cvv")
    private String cvv;

    public Payment() {
        super();
    }

    public Payment(Long order_id, String card_holder_name, Long card_num_used, String payment_status, Date expiration_date, String cvv) {
        this.order_id = order_id;
        this.card_holder_name = card_holder_name;
        this.card_num_used = card_num_used;
        this.payment_status = payment_status;
        this.expiration_date = expiration_date;
        this.cvv = cvv;
    }

    public Long getPayment_id() {
        return payment_id;
    }

    public void setPayment_id(Long payment_id) {
        this.payment_id = payment_id;
    }

    public Long getOrder_id() {
        return order_id;
    }

    public void setOrder_id(Long order_id) {
        this.order_id = order_id;
    }

    public String getCard_holder_name() {
        return card_holder_name;
    }

    public void setCard_holder_name(String card_holder_name) {
        this.card_holder_name = card_holder_name;
    }

    public Long getCard_num_used() {
        return card_num_used;
    }

    public void setCard_num_used(Long card_num_used) {
        this.card_num_used = card_num_used;
    }

    public String getPayment_status() {
        return payment_status;
    }

    public void setPayment_status(String payment_status) {
        this.payment_status = payment_status;
    }

    public Date getExpiration_date() {
        return expiration_date;
    }

    public void setExpiration_date(Date expiration_date) {
        this.expiration_date = expiration_date;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }
}