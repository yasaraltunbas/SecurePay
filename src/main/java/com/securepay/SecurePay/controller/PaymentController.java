package com.securepay.SecurePay.controller;

import com.securepay.SecurePay.dto.PaymentDto;
import com.securepay.SecurePay.entity.Payment;
import com.securepay.SecurePay.result.DataResult;
import com.securepay.SecurePay.result.ErrorDataResult;
import com.securepay.SecurePay.result.SuccessDataResult;
import com.securepay.SecurePay.service.ExceptionLogService;
import com.securepay.SecurePay.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    @Autowired
    private final PaymentService paymentService;
    @Autowired
    private ExceptionLogService exceptionLogService;


    public PaymentController(PaymentService paymentService, ExceptionLogService exceptionLogService) {
        this.paymentService = paymentService;
        this.exceptionLogService = exceptionLogService;
    }

    @PostMapping("/create")
    public DataResult<Payment> createPayment(@RequestParam() String cardNumber,
                                             @RequestParam() int expirationMonth,
                                             @RequestParam() int expirationYear,
                                             @RequestParam() int cvv,@RequestBody PaymentDto payment) {
        try {
            Payment newPayment = paymentService.createPayment(payment, cardNumber, expirationMonth, expirationYear, cvv);
            return new SuccessDataResult<>(newPayment, "Payment created successfully.");
        } catch (Exception e) {
            exceptionLogService.logException(e);
            return new ErrorDataResult<>(e.getMessage());
        }
    }

    @GetMapping("/search")
    public DataResult<List<Payment>> searchPayments(@RequestParam(required = false) String cardNumber,
                                                    @RequestParam(required = false) Long customerId,
                                                    @RequestParam(required = false) String customerName) throws Exception {
        try {
            List<Payment> payments = paymentService.searchPayments(cardNumber, customerId, customerName);
            return new SuccessDataResult<>(payments, "Payments found successfully.");
        } catch (Exception e) {
            exceptionLogService.logException(e);
            return new ErrorDataResult<>(e.getMessage());
        }

    }

    @GetMapping("/by-date")
    public DataResult<List<Payment>> getPaymentsByDateInterval(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate,int page, int size) {
        try {
            List<Payment> payments = paymentService.findPaymentsBetween(startDate, endDate);
            page = Math.max(page, 0);
            size = Math.max(size, 1);
            int start = page * size;
            int end = Math.min((start + size), payments.size());
            return new SuccessDataResult<>(payments.subList(start, end), "Payments found successfully.");
        } catch (Exception e) {
            exceptionLogService.logException(e);
            return new ErrorDataResult<>(e.getMessage());
        }
    }
}