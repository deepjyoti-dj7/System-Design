package com.airline.management.service;

import com.airline.management.dto.payment.PaymentRequest;
import com.airline.management.dto.payment.PaymentResponse;

import java.util.List;

public interface PaymentService {
    PaymentResponse createPayment(PaymentRequest request);
    PaymentResponse getPaymentById(Long id);
    List<PaymentResponse> getAllPayments();
}
