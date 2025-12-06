package com.urbanclone.booking.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookingCancelledEvent {
    
    private Long bookingId;
    
    private String bookingNumber;
    
    private Long customerId;
    
    private Long partnerId;
    
    private String reason;
    
    private LocalDateTime cancelledAt;
}
