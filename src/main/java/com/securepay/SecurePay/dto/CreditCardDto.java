package com.securepay.SecurePay.dto;


import com.securepay.SecurePay.entity.Customer;


public class CreditCardDto {

    private String cardNumber;
    private int expirationMonth;
    private int expirationYear;
    private int cvv;
    private String cardHolderName;

    public CreditCardDto() {
    }

    public CreditCardDto( String cardNumber, int cvv, String cardHolderName, Customer customer, int expirationMonth, int expirationYear) {
        this.cardNumber = cardNumber;
        this.cardHolderName = cardHolderName;
        this.expirationMonth = expirationMonth;
        this.expirationYear = expirationYear;
        this.cvv = cvv;
    }


    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public int getExpirationMonth() {
        return expirationMonth;
    }
    public void setExpirationMonth(int expirationMonth) {
        this.expirationMonth = expirationMonth;
    }
    public int getExpirationYear() {
        return expirationYear;
    }
    public void setExpirationYear(int expirationYear) {
        this.expirationYear = expirationYear;
    }

    public int getCvv() {
        return cvv;
    }

    public void setCvv(int cvv) {
        this.cvv = cvv;
    }

    public String getCardHolderName() {
        return cardHolderName;
    }

    public void setCardHolderName(String cardHolderName) {
        this.cardHolderName = cardHolderName;
    }
}
