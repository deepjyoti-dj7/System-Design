package com.urbanclone.booking.dto;

import com.urbanclone.booking.entity.PaymentMethod;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookingCreateRequest {
    
    @NotNull(message = "Service ID is required")
    private Long serviceId;
    
    @NotNull(message = "Scheduled time is required")
    @Future(message = "Scheduled time must be in the future")
    private LocalDateTime scheduledAt;
    
    @NotNull(message = "Address is required")
    private BookingAddressDto address;
    
    private String customerNotes;
    
    @NotNull(message = "Payment method is required")
    private PaymentMethod paymentMethod;
}
