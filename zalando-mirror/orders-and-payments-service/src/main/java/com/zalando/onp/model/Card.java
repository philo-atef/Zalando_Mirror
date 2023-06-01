package com.zalando.onp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;

@Entity
@Table(name = "Bank")
public class Card {
    @Id
    @Column(name = "credit")
    @Pattern(regexp = "\\d{16}", message = "Card number used must be a 16-digit number")
    private String credit;


    @Column(name = "balance")
    private Double balance;

    @Column(name = "card_holder_name")
    @Pattern(regexp = "^[A-Za-z ]+$", message = "Card holder name should only contain alphabetic characters")
    private String card_holder_name;

    @Column(name = "expiration_date")
    private String expiration_date;

    @Column(name = "cvv")
    @Pattern(regexp = "\\d{3}", message = "CVV must be a 3-digit number")
    private String cvv;

    public Card(String credit, Double balance) {
        this.credit = credit;
        this.balance = balance;
    }

    public Card() {
        super();
    }

    public String getCredit() {
        return credit;
    }

    public void setCredit(String credit) {
        this.credit = credit;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }
    public String getCard_holder_name() {
        return card_holder_name;
    }

    public void setCard_holder_name(String card_holder_name) {
        this.card_holder_name = card_holder_name;
    }

    public String getExpiration_date() {
        return expiration_date;
    }

    public void setExpiration_date(String expiration_date) {
        this.expiration_date = expiration_date;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }
}
