package com.urbanclone.payment.service;

import com.urbanclone.payment.dto.PaymentRequest;
import com.urbanclone.payment.dto.PaymentResponse;
import com.urbanclone.payment.dto.RefundRequest;
import com.urbanclone.payment.entity.Payment;
import com.urbanclone.payment.entity.PaymentGateway;
import com.urbanclone.payment.entity.PaymentStatus;
import com.urbanclone.payment.exception.PaymentProcessingException;
import com.urbanclone.payment.repository.PaymentRepository;
import com.urbanclone.payment.strategy.PaymentGatewayStrategy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentService {
    
    private final PaymentRepository paymentRepository;
    private final Map<PaymentGateway, PaymentGatewayStrategy> gatewayStrategies;
    private final KafkaTemplate<String, Object> kafkaTemplate;
    
    @Transactional
    public PaymentResponse initiatePayment(PaymentRequest request) {
        log.info("Initiating payment for booking: {}", request.getBookingId());
        
        // Create payment record
        Payment payment = Payment.builder()
                .transactionId(generateTransactionId())
                .bookingId(request.getBookingId())
                .customerId(request.getCustomerId())
                .amount(request.getAmount())
                .paymentMethod(request.getPaymentMethod())
                .status(PaymentStatus.INITIATED)
                .gateway(determineGateway(request))
                .build();
        
        payment = paymentRepository.save(payment);
        
        try {
            // Process payment through gateway
            PaymentGatewayStrategy strategy = gatewayStrategies.get(payment.getGateway());
            if (strategy == null) {
                throw new PaymentProcessingException("Gateway not supported");
            }
            
            String gatewayTxnId = strategy.processPayment(payment);
            
            // Update payment status
            payment.setStatus(PaymentStatus.SUCCESS);
            payment.setGatewayTransactionId(gatewayTxnId);
            payment.setProcessedAt(LocalDateTime.now());
            payment = paymentRepository.save(payment);
            
            // Publish payment success event
            publishPaymentSuccessEvent(payment);
            
            return mapToPaymentResponse(payment);
            
        } catch (Exception e) {
            log.error("Payment processing failed", e);
            payment.setStatus(PaymentStatus.FAILED);
            payment.setFailureReason(e.getMessage());
            paymentRepository.save(payment);
            
            publishPaymentFailedEvent(payment);
            
            throw new PaymentProcessingException("Payment failed: " + e.getMessage());
        }
    }
    
    @Transactional
    public PaymentResponse processRefund(Long paymentId, RefundRequest request) {
        log.info("Processing refund for payment: {}", paymentId);
        
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new PaymentProcessingException("Payment not found"));
        
        if (payment.getStatus() != PaymentStatus.SUCCESS) {
            throw new PaymentProcessingException("Payment not eligible for refund");
        }
        
        BigDecimal refundAmount = request.getAmount() != null ? 
                request.getAmount() : payment.getAmount();
        
        try {
            PaymentGatewayStrategy strategy = gatewayStrategies.get(payment.getGateway());
            strategy.processRefund(payment.getGatewayTransactionId(), refundAmount);
            
            payment.setStatus(PaymentStatus.REFUNDED);
            payment.setRefundedAmount(refundAmount);
            payment.setRefundedAt(LocalDateTime.now());
            payment = paymentRepository.save(payment);
            
            publishRefundProcessedEvent(payment);
            
            return mapToPaymentResponse(payment);
            
        } catch (Exception e) {
            log.error("Refund processing failed", e);
            throw new PaymentProcessingException("Refund failed: " + e.getMessage());
        }
    }
    
    @Transactional(readOnly = true)
    public PaymentResponse getPayment(Long paymentId) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new PaymentProcessingException("Payment not found"));
        return mapToPaymentResponse(payment);
    }
    
    @Transactional(readOnly = true)
    public PaymentResponse getPaymentByBooking(Long bookingId) {
        Payment payment = paymentRepository.findByBookingId(bookingId)
                .orElseThrow(() -> new PaymentProcessingException(
                        "Payment not found for booking: " + bookingId));
        return mapToPaymentResponse(payment);
    }
    
    private String generateTransactionId() {
        return "TXN" + System.currentTimeMillis() + 
               UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
    
    private PaymentGateway determineGateway(PaymentRequest request) {
        // Logic to determine which gateway to use based on payment method
        return switch (request.getPaymentMethod()) {
            case CREDIT_CARD, DEBIT_CARD -> PaymentGateway.STRIPE;
            case UPI, WALLET -> PaymentGateway.RAZORPAY;
            default -> PaymentGateway.STRIPE;
        };
    }
    
    private void publishPaymentSuccessEvent(Payment payment) {
        // Publish to Kafka
        log.info("Publishing payment success event: {}", payment.getTransactionId());
    }
    
    private void publishPaymentFailedEvent(Payment payment) {
        log.info("Publishing payment failed event: {}", payment.getTransactionId());
    }
    
    private void publishRefundProcessedEvent(Payment payment) {
        log.info("Publishing refund processed event: {}", payment.getTransactionId());
    }
    
    private PaymentResponse mapToPaymentResponse(Payment payment) {
        return PaymentResponse.builder()
                .id(payment.getId())
                .transactionId(payment.getTransactionId())
                .bookingId(payment.getBookingId())
                .amount(payment.getAmount())
                .status(payment.getStatus())
                .paymentMethod(payment.getPaymentMethod())
                .gateway(payment.getGateway())
                .processedAt(payment.getProcessedAt())
                .createdAt(payment.getCreatedAt())
                .build();
    }
}
