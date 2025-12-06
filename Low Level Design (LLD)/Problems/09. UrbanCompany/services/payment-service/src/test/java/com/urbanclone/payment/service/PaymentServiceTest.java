package com.urbanclone.payment.service;

import com.urbanclone.payment.dto.PaymentDto;
import com.urbanclone.payment.dto.PaymentRequest;
import com.urbanclone.payment.dto.RefundRequest;
import com.urbanclone.payment.entity.Payment;
import com.urbanclone.payment.entity.PaymentStatus;
import com.urbanclone.payment.exception.PaymentNotFoundException;
import com.urbanclone.payment.repository.PaymentRepository;
import com.urbanclone.payment.strategy.PaymentGatewayStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Payment Service Tests")
class PaymentServiceTest {

    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private Map<String, PaymentGatewayStrategy> paymentStrategies;

    @Mock
    private PaymentGatewayStrategy stripeStrategy;

    @Mock
    private PaymentGatewayStrategy razorpayStrategy;

    @InjectMocks
    private PaymentService paymentService;

    private Payment testPayment;
    private PaymentRequest paymentRequest;

    @BeforeEach
    void setUp() {
        testPayment = Payment.builder()
                .id(1L)
                .bookingId(100L)
                .userId(200L)
                .amount(BigDecimal.valueOf(50.00))
                .currency("USD")
                .status(PaymentStatus.PENDING)
                .gatewayType("STRIPE")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        paymentRequest = PaymentRequest.builder()
                .bookingId(100L)
                .userId(200L)
                .amount(BigDecimal.valueOf(50.00))
                .currency("USD")
                .gatewayType("STRIPE")
                .paymentMethodId("pm_123456")
                .build();

        when(paymentStrategies.get("STRIPE")).thenReturn(stripeStrategy);
        when(paymentStrategies.get("RAZORPAY")).thenReturn(razorpayStrategy);
    }

    @Test
    @DisplayName("Should process payment successfully")
    void shouldProcessPayment() {
        // Given
        when(stripeStrategy.processPayment(any(PaymentRequest.class))).thenReturn("charge_123456");
        when(paymentRepository.save(any(Payment.class))).thenReturn(testPayment);

        // When
        PaymentDto result = paymentService.processPayment(paymentRequest);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getBookingId()).isEqualTo(paymentRequest.getBookingId());
        assertThat(result.getAmount()).isEqualTo(paymentRequest.getAmount());

        ArgumentCaptor<Payment> paymentCaptor = ArgumentCaptor.forClass(Payment.class);
        verify(paymentRepository, times(2)).save(paymentCaptor.capture());
        Payment savedPayment = paymentCaptor.getValue();
        assertThat(savedPayment.getTransactionId()).isEqualTo("charge_123456");
    }

    @Test
    @DisplayName("Should handle payment failure")
    void shouldHandlePaymentFailure() {
        // Given
        when(stripeStrategy.processPayment(any(PaymentRequest.class)))
                .thenThrow(new RuntimeException("Payment declined"));
        when(paymentRepository.save(any(Payment.class))).thenReturn(testPayment);

        // When
        assertThatThrownBy(() -> paymentService.processPayment(paymentRequest))
                .isInstanceOf(RuntimeException.class);

        // Then
        ArgumentCaptor<Payment> paymentCaptor = ArgumentCaptor.forClass(Payment.class);
        verify(paymentRepository, times(2)).save(paymentCaptor.capture());
        Payment savedPayment = paymentCaptor.getValue();
        assertThat(savedPayment.getStatus()).isEqualTo(PaymentStatus.FAILED);
    }

    @Test
    @DisplayName("Should get payment by id successfully")
    void shouldGetPaymentById() {
        // Given
        when(paymentRepository.findById(1L)).thenReturn(Optional.of(testPayment));

        // When
        PaymentDto result = paymentService.getPaymentById(1L);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(testPayment.getId());
        assertThat(result.getAmount()).isEqualTo(testPayment.getAmount());
        verify(paymentRepository).findById(1L);
    }

    @Test
    @DisplayName("Should throw exception when payment not found")
    void shouldThrowExceptionWhenPaymentNotFound() {
        // Given
        when(paymentRepository.findById(1L)).thenReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> paymentService.getPaymentById(1L))
                .isInstanceOf(PaymentNotFoundException.class)
                .hasMessageContaining("Payment not found with id: 1");
    }

    @Test
    @DisplayName("Should get payment by booking id")
    void shouldGetPaymentByBookingId() {
        // Given
        when(paymentRepository.findByBookingId(100L)).thenReturn(Optional.of(testPayment));

        // When
        PaymentDto result = paymentService.getPaymentByBookingId(100L);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getBookingId()).isEqualTo(100L);
        verify(paymentRepository).findByBookingId(100L);
    }

    @Test
    @DisplayName("Should refund payment successfully")
    void shouldRefundPayment() {
        // Given
        testPayment.setStatus(PaymentStatus.COMPLETED);
        testPayment.setTransactionId("charge_123456");
        
        RefundRequest refundRequest = RefundRequest.builder()
                .paymentId(1L)
                .amount(BigDecimal.valueOf(50.00))
                .reason("Customer request")
                .build();

        when(paymentRepository.findById(1L)).thenReturn(Optional.of(testPayment));
        when(stripeStrategy.refundPayment(any(String.class), any(BigDecimal.class)))
                .thenReturn("refund_123456");
        when(paymentRepository.save(any(Payment.class))).thenReturn(testPayment);

        // When
        PaymentDto result = paymentService.refundPayment(refundRequest);

        // Then
        assertThat(result).isNotNull();
        ArgumentCaptor<Payment> paymentCaptor = ArgumentCaptor.forClass(Payment.class);
        verify(paymentRepository).save(paymentCaptor.capture());
        Payment refundedPayment = paymentCaptor.getValue();
        assertThat(refundedPayment.getStatus()).isEqualTo(PaymentStatus.REFUNDED);
        assertThat(refundedPayment.getRefundTransactionId()).isEqualTo("refund_123456");
    }

    @Test
    @DisplayName("Should not refund payment that is not completed")
    void shouldNotRefundNonCompletedPayment() {
        // Given
        testPayment.setStatus(PaymentStatus.PENDING);
        
        RefundRequest refundRequest = RefundRequest.builder()
                .paymentId(1L)
                .amount(BigDecimal.valueOf(50.00))
                .reason("Customer request")
                .build();

        when(paymentRepository.findById(1L)).thenReturn(Optional.of(testPayment));

        // When & Then
        assertThatThrownBy(() -> paymentService.refundPayment(refundRequest))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("Payment is not in COMPLETED status");

        verify(paymentRepository, never()).save(any(Payment.class));
    }

    @Test
    @DisplayName("Should use correct payment gateway strategy")
    void shouldUseCorrectStrategy() {
        // Given
        PaymentRequest razorpayRequest = PaymentRequest.builder()
                .bookingId(100L)
                .userId(200L)
                .amount(BigDecimal.valueOf(50.00))
                .currency("INR")
                .gatewayType("RAZORPAY")
                .paymentMethodId("pm_789012")
                .build();

        when(razorpayStrategy.processPayment(any(PaymentRequest.class))).thenReturn("order_123456");
        when(paymentRepository.save(any(Payment.class))).thenReturn(testPayment);

        // When
        paymentService.processPayment(razorpayRequest);

        // Then
        verify(razorpayStrategy).processPayment(any(PaymentRequest.class));
        verify(stripeStrategy, never()).processPayment(any(PaymentRequest.class));
    }

    @Test
    @DisplayName("Should verify payment status")
    void shouldVerifyPaymentStatus() {
        // Given
        testPayment.setStatus(PaymentStatus.PENDING);
        testPayment.setTransactionId("charge_123456");

        when(paymentRepository.findById(1L)).thenReturn(Optional.of(testPayment));
        when(stripeStrategy.verifyPayment("charge_123456")).thenReturn(true);
        when(paymentRepository.save(any(Payment.class))).thenReturn(testPayment);

        // When
        PaymentDto result = paymentService.verifyPaymentStatus(1L);

        // Then
        assertThat(result).isNotNull();
        ArgumentCaptor<Payment> paymentCaptor = ArgumentCaptor.forClass(Payment.class);
        verify(paymentRepository).save(paymentCaptor.capture());
        Payment verifiedPayment = paymentCaptor.getValue();
        assertThat(verifiedPayment.getStatus()).isEqualTo(PaymentStatus.COMPLETED);
    }
}
