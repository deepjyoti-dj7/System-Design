package com.urbanclone.booking.service;

import com.urbanclone.booking.dto.PricingDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
@Slf4j
public class PricingService {
    
    private static final BigDecimal TAX_RATE = new BigDecimal("0.18"); // 18% GST
    
    public PricingDetails calculatePrice(Long serviceId) {
        // In real implementation, fetch service details from catalog service
        BigDecimal basePrice = getServiceBasePrice(serviceId);
        BigDecimal discount = calculateDiscount(serviceId, basePrice);
        BigDecimal discountedPrice = basePrice.subtract(discount);
        BigDecimal tax = discountedPrice.multiply(TAX_RATE).setScale(2, RoundingMode.HALF_UP);
        BigDecimal totalPrice = discountedPrice.add(tax).setScale(2, RoundingMode.HALF_UP);
        
        return PricingDetails.builder()
                .basePrice(basePrice)
                .discount(discount)
                .tax(tax)
                .totalPrice(totalPrice)
                .build();
    }
    
    private BigDecimal getServiceBasePrice(Long serviceId) {
        // Mock implementation - should fetch from catalog service
        return new BigDecimal("500.00");
    }
    
    private BigDecimal calculateDiscount(Long serviceId, BigDecimal basePrice) {
        // Mock implementation - apply promotional discounts
        return BigDecimal.ZERO;
    }
}
