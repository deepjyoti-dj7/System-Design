package com.urbanclone.payment.strategy;

import com.urbanclone.payment.entity.Payment;

import java.math.BigDecimal;

public interface PaymentGatewayStrategy {
    
    String processPayment(Payment payment) throws Exception;
    
    void processRefund(String transactionId, BigDecimal amount) throws Exception;
}
