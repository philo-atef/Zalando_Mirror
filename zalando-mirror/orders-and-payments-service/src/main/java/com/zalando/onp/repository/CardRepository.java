package com.zalando.onp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.zalando.onp.model.Card;

public interface CardRepository extends JpaRepository<Card, String> {
    Card findByCredit(String credit);

}