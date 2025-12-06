package com.urbanclone.booking.dto;

import com.urbanclone.booking.entity.BookingStatus;
import com.urbanclone.booking.entity.PaymentMethod;
import com.urbanclone.booking.entity.PaymentStatus;
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
public class BookingDto {
    
    private Long id;
    
    private String bookingNumber;
    
    private Long customerId;
    
    private Long serviceId;
    
    private String serviceName;
    
    private Long partnerId;
    
    private String partnerName;
    
    private LocalDateTime scheduledAt;
    
    private BookingStatus status;
    
    private BookingAddressDto address;
    
    private BigDecimal basePrice;
    
    private BigDecimal discount;
    
    private BigDecimal tax;
    
    private BigDecimal totalPrice;
    
    private String customerNotes;
    
    private String cancellationReason;
    
    private PaymentStatus paymentStatus;
    
    private PaymentMethod paymentMethod;
    
    private LocalDateTime createdAt;
    
    private LocalDateTime completedAt;
}
