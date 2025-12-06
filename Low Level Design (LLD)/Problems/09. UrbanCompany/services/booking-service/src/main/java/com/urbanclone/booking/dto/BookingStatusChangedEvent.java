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
public class BookingStatusChangedEvent {
    
    private Long bookingId;
    
    private String bookingNumber;
    
    private String oldStatus;
    
    private String newStatus;
    
    private LocalDateTime changedAt;
}
