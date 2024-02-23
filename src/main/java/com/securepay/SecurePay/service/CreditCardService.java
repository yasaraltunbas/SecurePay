package com.securepay.SecurePay.service;

import com.securepay.SecurePay.util.EncryptionUtil;
import com.securepay.SecurePay.dto.CreditCardDto;
import com.securepay.SecurePay.entity.Card;
import com.securepay.SecurePay.repository.CardRepository;
import com.securepay.SecurePay.repository.CustomerRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;


@Service
public class CreditCardService {

    private static final String ENCRYPTION_ALGORITHM = "AES";

    ModelMapper modelMapper;
    CustomerRepository customerRepository;
    CardRepository cardRepository;

    public CreditCardService(CardRepository cardRepository, CustomerRepository customerRepository, ModelMapper modelMapper) {
        this.cardRepository = cardRepository;
        this.customerRepository = customerRepository;
        this.modelMapper = modelMapper;

    }

    public Card addCard(Long customerId, CreditCardDto cardDto) throws Exception {
        validateCard(cardDto, customerId);

        cardDto.setCardNumber(EncryptionUtil.encrypt(cardDto.getCardNumber()));
        Card card = modelMapper.map(cardDto, Card.class);
        card.setCustomerId(customerId);
        return cardRepository.save(card);
    }

    private void validateCard(CreditCardDto cardDto, Long customerId) throws Exception {
        if (cardDto.getCardNumber() == null || cardDto.getCardNumber().isEmpty()) {
            throw new IllegalArgumentException("Card number is required");
        }
        if (cardDto.getExpirationMonth() <= 0 || cardDto.getExpirationMonth() > 12) {
            throw new IllegalArgumentException("Invalid expiration month");
        }
        if ( cardDto.getExpirationYear() < 2024) {
            throw new IllegalArgumentException("Invalid expiration year");
        }
        if (cardDto.getCvv() < 100 || cardDto.getCvv() > 999) {
            throw new IllegalArgumentException("Invalid CVV");
        }
        if (cardDto.getCardHolderName() == null || cardDto.getCardHolderName().isEmpty()) {
            throw new IllegalArgumentException("Card holder name is required");
        }
        customerRepository.findById(customerId)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found"));

        cardRepository.findByCardNumber(EncryptionUtil.encrypt(cardDto.getCardNumber()))
                .ifPresent(card -> {
                    throw new IllegalArgumentException("Card already exists");
                });

    }

    public Card updateCard(Long customerId, Long cardId, CreditCardDto cardDto) throws Exception {
        validateCard(cardDto, customerId);
        cardRepository.findById(cardId)
                .orElseThrow(() -> new IllegalArgumentException("Card not found"));
        cardDto.setCardNumber(EncryptionUtil.encrypt(cardDto.getCardNumber()));
        Card card = modelMapper.map(cardDto, Card.class);
        card.setCustomerId(customerId);
        card.setCardId(cardId);
        return cardRepository.save(card);
    }
}
