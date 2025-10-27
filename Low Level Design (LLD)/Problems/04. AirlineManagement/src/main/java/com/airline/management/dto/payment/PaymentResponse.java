package com.airline.management.dto.payment;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class PaymentResponse {
    private Long id;
    private Long bookingId;
    private double amount;
    private String paymentMethod;
    private LocalDateTime paymentTime;
    private String status;
}
