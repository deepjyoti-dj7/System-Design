package com.airline.management.dto.payment;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PaymentRequest {

    @NotNull
    private Long bookingId;

    @Min(0)
    private double amount;

    @NotBlank
    private String paymentMethod;
}
