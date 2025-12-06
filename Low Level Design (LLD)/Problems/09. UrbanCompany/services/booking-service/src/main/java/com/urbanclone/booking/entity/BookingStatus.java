package com.urbanclone.booking.entity;

public enum BookingStatus {
    PENDING,              // Booking created, waiting for partner assignment
    PARTNER_ASSIGNED,     // Partner assigned to booking
    PARTNER_EN_ROUTE,     // Partner on the way
    PARTNER_ARRIVED,      // Partner reached customer location
    IN_PROGRESS,          // Service in progress
    COMPLETED,            // Service completed
    CANCELLED,            // Booking cancelled
    PAYMENT_PENDING,      // Waiting for payment
    PAYMENT_FAILED        // Payment failed
}
