package com.securepay.SecurePay.repository;

import com.securepay.SecurePay.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CardRepository  extends JpaRepository<Card,Long> {
    Optional<Card> findByCardNumber(String cardNumber);

}