package com.securepay.SecurePay.service;


import com.securepay.SecurePay.dto.CustomerDto;

import com.securepay.SecurePay.entity.Customer;
import com.securepay.SecurePay.repository.CardRepository;
import com.securepay.SecurePay.repository.CustomerRepository;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;


@Service
public class CustomerService {
    public CustomerRepository customerRepository;
    public CardRepository cardRepository;
    private final ModelMapper modelMapper;

    public CustomerService(CustomerRepository customerRepository, ModelMapper modelMapper, CardRepository cardRepository){
        this.customerRepository = customerRepository;
        this.modelMapper = modelMapper;
        this.cardRepository = cardRepository;
    }

    public Customer registerCustomer(CustomerDto customerDto) {
        if (customerRepository.existsByEmail(customerDto.getEmail())) {
            throw new IllegalArgumentException("Email already in use");
        }
        Customer customer = modelMapper.map(customerDto, Customer.class);
        return customerRepository.save(customer);
    }



}
