package com.urbanclone.payment.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.urbanclone.payment.dto.PaymentDto;
import com.urbanclone.payment.dto.PaymentRequest;
import com.urbanclone.payment.dto.RefundRequest;
import com.urbanclone.payment.entity.PaymentStatus;
import com.urbanclone.payment.service.PaymentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PaymentController.class)
@DisplayName("Payment Controller Tests")
class PaymentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PaymentService paymentService;

    private PaymentDto testPaymentDto;
    private PaymentRequest paymentRequest;

    @BeforeEach
    void setUp() {
        testPaymentDto = PaymentDto.builder()
                .id(1L)
                .bookingId(100L)
                .userId(200L)
                .amount(BigDecimal.valueOf(50.00))
                .currency("USD")
                .status(PaymentStatus.COMPLETED)
                .gatewayType("STRIPE")
                .transactionId("charge_123456")
                .createdAt(LocalDateTime.now())
                .build();

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
    void shouldProcessPayment() throws Exception {
        // Given
        when(paymentService.processPayment(any(PaymentRequest.class)))
                .thenReturn(testPaymentDto);

        // When & Then
        mockMvc.perform(post("/api/v1/payments/process")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(paymentRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.bookingId").value(100))
                .andExpect(jsonPath("$.status").value("COMPLETED"))
                .andExpect(jsonPath("$.transactionId").value("charge_123456"));
    }

    @Test
    @DisplayName("Should get payment by id successfully")
    void shouldGetPaymentById() throws Exception {
        // Given
        when(paymentService.getPaymentById(1L)).thenReturn(testPaymentDto);

        // When & Then
        mockMvc.perform(get("/api/v1/payments/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.status").value("COMPLETED"));
    }

    @Test
    @DisplayName("Should get payment by booking id")
    void shouldGetPaymentByBookingId() throws Exception {
        // Given
        when(paymentService.getPaymentByBookingId(100L)).thenReturn(testPaymentDto);

        // When & Then
        mockMvc.perform(get("/api/v1/payments/booking/100"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.bookingId").value(100));
    }

    @Test
    @DisplayName("Should refund payment successfully")
    void shouldRefundPayment() throws Exception {
        // Given
        testPaymentDto.setStatus(PaymentStatus.REFUNDED);
        RefundRequest refundRequest = RefundRequest.builder()
                .paymentId(1L)
                .amount(BigDecimal.valueOf(50.00))
                .reason("Customer request")
                .build();

        when(paymentService.refundPayment(any(RefundRequest.class))).thenReturn(testPaymentDto);

        // When & Then
        mockMvc.perform(post("/api/v1/payments/refund")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(refundRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("REFUNDED"));
    }

    @Test
    @DisplayName("Should return bad request when amount is invalid")
    void shouldReturnBadRequestWhenAmountInvalid() throws Exception {
        // Given
        paymentRequest.setAmount(BigDecimal.ZERO);

        // When & Then
        mockMvc.perform(post("/api/v1/payments/process")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(paymentRequest)))
                .andExpect(status().isBadRequest());
    }
}
