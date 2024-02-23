package com.securepay.SecurePay.dto;

import java.math.BigDecimal;
import java.time.LocalDate;


public class PaymentDto {
    private BigDecimal amount;
    private LocalDate date;
    private String description;
    private String status;

    public PaymentDto() {
    }

    public PaymentDto(BigDecimal amount, LocalDate date, String description, String status, String cardNumber) {
        this.amount = amount;
        this.date = date;
        this.description = description;
        this.status = status;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}