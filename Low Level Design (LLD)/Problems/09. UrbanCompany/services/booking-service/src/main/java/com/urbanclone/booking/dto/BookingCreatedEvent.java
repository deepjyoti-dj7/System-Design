package com.urbanclone.booking.dto;

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
public class BookingCreatedEvent {
    
    private Long bookingId;
    
    private String bookingNumber;
    
    private Long customerId;
    
    private Long serviceId;
    
    private LocalDateTime scheduledAt;
    
    private BigDecimal totalPrice;
    
    private LocalDateTime createdAt;
}
