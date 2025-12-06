package com.urbanclone.booking.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Pricing Service Tests")
class PricingServiceTest {

    @InjectMocks
    private PricingService pricingService;

    private Long serviceId;
    private LocalDateTime scheduledTime;

    @BeforeEach
    void setUp() {
        serviceId = 200L;
    }

    @Test
    @DisplayName("Should calculate base price correctly")
    void shouldCalculateBasePrice() {
        // Given
        scheduledTime = LocalDateTime.of(2025, 12, 15, 14, 0); // Monday afternoon

        // When
        BigDecimal price = pricingService.calculatePrice(serviceId, scheduledTime);

        // Then
        assertThat(price).isNotNull();
        assertThat(price).isGreaterThan(BigDecimal.ZERO);
    }

    @Test
    @DisplayName("Should apply weekend surcharge")
    void shouldApplyWeekendSurcharge() {
        // Given
        LocalDateTime weekday = LocalDateTime.of(2025, 12, 15, 14, 0); // Monday
        LocalDateTime weekend = LocalDateTime.of(2025, 12, 20, 14, 0); // Saturday

        // When
        BigDecimal weekdayPrice = pricingService.calculatePrice(serviceId, weekday);
        BigDecimal weekendPrice = pricingService.calculatePrice(serviceId, weekend);

        // Then
        assertThat(weekendPrice).isGreaterThan(weekdayPrice);
    }

    @Test
    @DisplayName("Should apply peak hours surcharge")
    void shouldApplyPeakHoursSurcharge() {
        // Given
        LocalDateTime offPeak = LocalDateTime.of(2025, 12, 15, 14, 0); // 2 PM
        LocalDateTime peak = LocalDateTime.of(2025, 12, 15, 19, 0); // 7 PM

        // When
        BigDecimal offPeakPrice = pricingService.calculatePrice(serviceId, offPeak);
        BigDecimal peakPrice = pricingService.calculatePrice(serviceId, peak);

        // Then
        assertThat(peakPrice).isGreaterThan(offPeakPrice);
    }

    @Test
    @DisplayName("Should apply early morning surcharge")
    void shouldApplyEarlyMorningSurcharge() {
        // Given
        LocalDateTime normalTime = LocalDateTime.of(2025, 12, 15, 10, 0); // 10 AM
        LocalDateTime earlyMorning = LocalDateTime.of(2025, 12, 15, 6, 0); // 6 AM

        // When
        BigDecimal normalPrice = pricingService.calculatePrice(serviceId, normalTime);
        BigDecimal earlyMorningPrice = pricingService.calculatePrice(serviceId, earlyMorning);

        // Then
        assertThat(earlyMorningPrice).isGreaterThan(normalPrice);
    }
}
