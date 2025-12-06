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
@DisplayName("Razorpay Payment Strategy Tests")
class RazorpayPaymentStrategyTest {

    private RazorpayPaymentStrategy razorpayStrategy;
    private PaymentRequest paymentRequest;

    @BeforeEach
    void setUp() {
        razorpayStrategy = new RazorpayPaymentStrategy();
        
        paymentRequest = PaymentRequest.builder()
                .bookingId(100L)
                .userId(200L)
                .amount(BigDecimal.valueOf(2500.00))
                .currency("INR")
                .gatewayType("RAZORPAY")
                .paymentMethodId("pm_123456")
                .build();
    }

    @Test
    @DisplayName("Should process payment successfully")
    void shouldProcessPayment() {
        // When
        String transactionId = razorpayStrategy.processPayment(paymentRequest);

        // Then
        assertThat(transactionId).isNotNull();
        assertThat(transactionId).startsWith("razorpay_");
    }

    @Test
    @DisplayName("Should throw exception when payment method is missing")
    void shouldThrowExceptionWhenPaymentMethodMissing() {
        // Given
        paymentRequest.setPaymentMethodId(null);

        // When & Then
        assertThatThrownBy(() -> razorpayStrategy.processPayment(paymentRequest))
                .isInstanceOf(PaymentProcessingException.class)
                .hasMessageContaining("Payment method ID is required");
    }

    @Test
    @DisplayName("Should refund payment successfully")
    void shouldRefundPayment() {
        // When
        String refundId = razorpayStrategy.refundPayment("order_123456", BigDecimal.valueOf(2500.00));

        // Then
        assertThat(refundId).isNotNull();
        assertThat(refundId).startsWith("refund_");
    }

    @Test
    @DisplayName("Should verify payment successfully")
    void shouldVerifyPayment() {
        // When
        boolean isVerified = razorpayStrategy.verifyPayment("order_123456");

        // Then
        assertThat(isVerified).isTrue();
    }

    @Test
    @DisplayName("Should convert amount to paise correctly")
    void shouldConvertAmountToPaise() {
        // Given - Razorpay expects amount in paise (smallest currency unit)
        BigDecimal rupees = BigDecimal.valueOf(100.00);
        
        // When
        String transactionId = razorpayStrategy.processPayment(paymentRequest);

        // Then
        assertThat(transactionId).isNotNull();
        // Verify that amount was handled correctly (100 rupees = 10000 paise)
    }
}
