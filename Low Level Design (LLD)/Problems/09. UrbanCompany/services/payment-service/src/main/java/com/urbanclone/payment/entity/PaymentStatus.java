package com.urbanclone.payment.entity;

public enum PaymentStatus {
    INITIATED,
    PROCESSING,
    SUCCESS,
    FAILED,
    REFUND_INITIATED,
    REFUNDED,
    PARTIALLY_REFUNDED
}
