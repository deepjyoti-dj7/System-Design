package com.urbanclone.payment.controller;

import com.urbanclone.payment.dto.PaymentRequest;
import com.urbanclone.payment.dto.PaymentResponse;
import com.urbanclone.payment.dto.RefundRequest;
import com.urbanclone.payment.service.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
public class PaymentController {
    
    private final PaymentService paymentService;
    
    @PostMapping
    public ResponseEntity<PaymentResponse> initiatePayment(@Valid @RequestBody PaymentRequest request) {
        PaymentResponse response = paymentService.initiatePayment(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
    @GetMapping("/{paymentId}")
    public ResponseEntity<PaymentResponse> getPayment(@PathVariable Long paymentId) {
        PaymentResponse response = paymentService.getPayment(paymentId);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/booking/{bookingId}")
    public ResponseEntity<PaymentResponse> getPaymentByBooking(@PathVariable Long bookingId) {
        PaymentResponse response = paymentService.getPaymentByBooking(bookingId);
        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/{paymentId}/refund")
    public ResponseEntity<PaymentResponse> processRefund(
            @PathVariable Long paymentId,
            @Valid @RequestBody RefundRequest request) {
        PaymentResponse response = paymentService.processRefund(paymentId, request);
        return ResponseEntity.ok(response);
    }
}
