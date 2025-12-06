package com.urbanclone.payment.dto;

import com.urbanclone.payment.entity.PaymentMethod;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentRequest {
    
    @NotNull
    private Long bookingId;
    
    @NotNull
    private Long customerId;
    
    @NotNull
    @Positive
    private BigDecimal amount;
    
    @NotNull
    private PaymentMethod paymentMethod;
}
