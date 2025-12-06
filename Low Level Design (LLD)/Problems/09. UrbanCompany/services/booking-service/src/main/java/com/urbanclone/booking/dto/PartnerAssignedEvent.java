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
public class PartnerAssignedEvent {
    
    private Long bookingId;
    
    private String bookingNumber;
    
    private Long partnerId;
    
    private Long customerId;
    
    private LocalDateTime scheduledAt;
}
