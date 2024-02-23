package com.securepay.SecurePay.controller;


import com.securepay.SecurePay.dto.CreditCardDto;
import com.securepay.SecurePay.dto.CustomerDto;
import com.securepay.SecurePay.entity.Card;
import com.securepay.SecurePay.entity.Customer;
import com.securepay.SecurePay.result.*;
import com.securepay.SecurePay.service.CreditCardService;
import com.securepay.SecurePay.service.CustomerService;
import com.securepay.SecurePay.service.ExceptionLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class CustomerController {

    @Autowired
    CustomerService customerService;
    @Autowired
    CreditCardService creditCardService;
    @Autowired
    ExceptionLogService exceptionLogService;

    public CustomerController(CustomerService customerService, CreditCardService creditCardService, ExceptionLogService exceptionLogService) {
        this.customerService = customerService;
        this.creditCardService = creditCardService;
        this.exceptionLogService = exceptionLogService;
    }

    @PostMapping("/register")
    public DataResult<Customer> registerCustomer(@RequestBody CustomerDto customer) {
        try {
            Customer registeredCustomer = customerService.registerCustomer(customer);
            return new SuccessDataResult<Customer>(registeredCustomer, "Customer registered successfully.");
        } catch (Exception e) {
            exceptionLogService.logException(e);
            return new ErrorDataResult<>(e.getMessage());
        }
    }

    @PostMapping("/{customerId}/addCard")
    public DataResult<Card> addCustomerCard(@PathVariable Long customerId, @RequestBody CreditCardDto card) throws Exception {
        try {
            Card savedCard = creditCardService.addCard(customerId, card);
            return new SuccessDataResult<Card>(savedCard, "Card added successfully.");
        } catch (Exception e) {
            exceptionLogService.logException(e);
            return new ErrorDataResult<>(e.getMessage());
        }
    }

    @PutMapping("/{customerId}/cards/{cardId}")
    public DataResult<Card> updateCustomerCard(@PathVariable Long customerId, @PathVariable Long cardId, @RequestBody CreditCardDto card) throws Exception {
        try {
            Card savedCard = creditCardService.updateCard(customerId, cardId, card);
            return new SuccessDataResult<Card>(savedCard, "Card updated successfully.");
        } catch (Exception e) {
            exceptionLogService.logException(e);
            return new ErrorDataResult<>(e.getMessage());
        }

    }
}