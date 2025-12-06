package com.urbanclone.booking.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PricingDetails {
    
    private BigDecimal basePrice;
    
    private BigDecimal discount;
    
    private BigDecimal tax;
    
    private BigDecimal totalPrice;
}
