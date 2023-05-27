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

}
