package com.urbanclone.payment.dto;

import com.urbanclone.payment.entity.PaymentGateway;
import com.urbanclone.payment.entity.PaymentMethod;
import com.urbanclone.payment.entity.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentResponse {
    
    private Long id;
    
    private String transactionId;
    
    private Long bookingId;
    
    private BigDecimal amount;
    
    private PaymentStatus status;
    
    private PaymentMethod paymentMethod;
    
    private PaymentGateway gateway;
    
    private LocalDateTime processedAt;
    
    private LocalDateTime createdAt;
}
