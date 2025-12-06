package com.urbanclone.payment.strategy;

import com.urbanclone.payment.dto.PaymentRequest;
import com.urbanclone.payment.exception.PaymentProcessingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
@DisplayName("Stripe Payment Strategy Tests")
class StripePaymentStrategyTest {

    private StripePaymentStrategy stripeStrategy;
    private PaymentRequest paymentRequest;

    @BeforeEach
    void setUp() {
        stripeStrategy = new StripePaymentStrategy();
        
        paymentRequest = PaymentRequest.builder()
                .bookingId(100L)
                .userId(200L)
                .amount(BigDecimal.valueOf(50.00))
                .currency("USD")
                .gatewayType("STRIPE")
                .paymentMethodId("pm_123456")
                .build();
    }

    @Test
    @DisplayName("Should process payment successfully")
    void shouldProcessPayment() {
        // When
        String transactionId = stripeStrategy.processPayment(paymentRequest);

        // Then
        assertThat(transactionId).isNotNull();
        assertThat(transactionId).startsWith("stripe_");
    }

    @Test
    @DisplayName("Should throw exception when payment method is missing")
    void shouldThrowExceptionWhenPaymentMethodMissing() {
        // Given
        paymentRequest.setPaymentMethodId(null);

        // When & Then
        assertThatThrownBy(() -> stripeStrategy.processPayment(paymentRequest))
                .isInstanceOf(PaymentProcessingException.class)
                .hasMessageContaining("Payment method ID is required");
    }

    @Test
    @DisplayName("Should throw exception when amount is invalid")
    void shouldThrowExceptionWhenAmountInvalid() {
        // Given
        paymentRequest.setAmount(BigDecimal.ZERO);

        // When & Then
        assertThatThrownBy(() -> stripeStrategy.processPayment(paymentRequest))
                .isInstanceOf(PaymentProcessingException.class)
                .hasMessageContaining("Amount must be greater than zero");
    }

    @Test
    @DisplayName("Should refund payment successfully")
    void shouldRefundPayment() {
        // When
        String refundId = stripeStrategy.refundPayment("charge_123456", BigDecimal.valueOf(50.00));

        // Then
        assertThat(refundId).isNotNull();
        assertThat(refundId).startsWith("refund_");
    }

    @Test
    @DisplayName("Should verify payment successfully")
    void shouldVerifyPayment() {
        // When
        boolean isVerified = stripeStrategy.verifyPayment("charge_123456");

        // Then
        assertThat(isVerified).isTrue();
    }

    @Test
    @DisplayName("Should handle different currencies")
    void shouldHandleDifferentCurrencies() {
        // Given
        PaymentRequest eurRequest = PaymentRequest.builder()
                .bookingId(100L)
                .userId(200L)
                .amount(BigDecimal.valueOf(45.00))
                .currency("EUR")
                .gatewayType("STRIPE")
                .paymentMethodId("pm_789012")
                .build();

        // When
        String transactionId = stripeStrategy.processPayment(eurRequest);

        // Then
        assertThat(transactionId).isNotNull();
    }
}
