package com.securepay.SecurePay.service;

import com.securepay.SecurePay.util.EncryptionUtil;
import com.securepay.SecurePay.dto.PaymentDto;
import com.securepay.SecurePay.entity.Card;
import com.securepay.SecurePay.entity.Payment;
import com.securepay.SecurePay.repository.CardRepository;
import com.securepay.SecurePay.repository.CustomerRepository;
import com.securepay.SecurePay.repository.PaymentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@Service
public class PaymentService {
    public PaymentRepository paymentRepository;
    public CustomerRepository customerRepository;
    public CardRepository cardRepository;
    ModelMapper modelMapper;

    public PaymentService(PaymentRepository paymentRepository, CustomerRepository customerRepository, CardRepository cardRepository, ModelMapper modelMapper) {
        this.paymentRepository = paymentRepository;
        this.customerRepository = customerRepository;
        this.cardRepository = cardRepository;
        this.modelMapper = modelMapper;
    }

    public Payment createPayment(PaymentDto paymentDto, String cardNumber, int expirationMonth, int expirationYear, int cvv) throws Exception {

        validatePayment(paymentDto, cardNumber, expirationMonth, expirationYear, cvv);

        Card card = cardRepository.findByCardNumber(EncryptionUtil.encrypt(cardNumber)).get();
        String customerName = customerRepository.findById(card.getCustomerId())
                .orElseThrow(() -> new IllegalArgumentException("Customer not found")).getName();

        Payment payment = modelMapper.map(paymentDto, Payment.class);
        payment.setCardNumber(EncryptionUtil.encrypt(cardNumber));
        payment.setCustomerName(customerName);
        payment.setCustomerId(card.getCustomerId());
        return paymentRepository.save(payment);
    }

    public List<Payment> searchPayments(String cardNumber, Long customerId, String customerName) throws Exception {
        if (cardNumber != null && !cardNumber.isEmpty()) {
            return paymentRepository.findByCardNumber(EncryptionUtil.encrypt(cardNumber));
        } else if (customerId != null) {
            return paymentRepository.findByCustomerId(customerId);
        } else if (customerName != null && !customerName.isEmpty()) {
            return customerRepository.findByName(customerName)
                    .map(customer -> paymentRepository.findByCustomerId(customer.getCustomerId()))
                    .orElse(Collections.emptyList());
        }
        return Collections.emptyList();
    }

    public List<Payment> findPaymentsBetween(LocalDate startDate, LocalDate endDate) {
        validateDates(startDate, endDate);
        return paymentRepository.findByDateBetween(startDate, endDate);
    }

    private void validateDates(LocalDate startDate, LocalDate endDate) {
        if (startDate == null || endDate == null) {
            throw new IllegalArgumentException("Start and end date are required");
        }
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("Start date must be before end date");
        }

        if (startDate.equals(endDate)) {
            throw new IllegalArgumentException("Start and end date cannot be the same");
        }
        if (endDate.isBefore(startDate)) {
            throw new IllegalArgumentException("End date must be after start date");
        }
    }

    private void validatePayment(PaymentDto paymentDto, String cardNumber, int expirationMonth, int expirationYear, int cvv) throws Exception {
        if (paymentDto.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Payment amount must be greater than 0");
        }
        if (paymentDto.getDate() == null) {
            throw new IllegalArgumentException("Payment date is required");
        }
        if (paymentDto.getDescription() == null || paymentDto.getDescription().isEmpty()) {
            throw new IllegalArgumentException("Payment description is required");
        }
        if (cardNumber == null || cardNumber.isEmpty()) {
            throw new IllegalArgumentException("Card number is required");
        }
        if (expirationMonth < 1 || expirationMonth > 12) {
            throw new IllegalArgumentException("Invalid expiration month");
        }
        if (expirationYear < 2024) {
            throw new IllegalArgumentException("Invalid expiration year");
        }
        if (cvv < 100 || cvv > 999) {
            throw new IllegalArgumentException("Invalid CVV");
        }

        Card card = cardRepository.findByCardNumber(EncryptionUtil.encrypt(cardNumber))
                .orElseThrow(() -> new IllegalArgumentException("Card not found"));

        if (card.getExpirationMonth() != expirationMonth || card.getExpirationYear() != expirationYear || card.getCvv() != cvv) {
            throw new IllegalArgumentException("Invalid card information");
        }

    }
}