package com.airline.management.event.model;

import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentCompletedEvent {
    private Long paymentId;
    private Long bookingId;
    private double amount;
    private String status;
    private LocalDateTime paymentTime;
}
