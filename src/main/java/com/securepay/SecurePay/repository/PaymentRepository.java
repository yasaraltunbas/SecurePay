package com.securepay.SecurePay.repository;

import com.securepay.SecurePay.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment,Long> {
    List<Payment> findByCardNumber(String cardNumber);
    List<Payment> findByCustomerId(Long customerId);
    List<Payment> findByDateBetween(LocalDate startDate, LocalDate endDate);
}