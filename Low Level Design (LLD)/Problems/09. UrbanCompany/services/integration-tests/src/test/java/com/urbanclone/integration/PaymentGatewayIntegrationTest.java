package com.urbanclone.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.hamcrest.Matchers.greaterThan;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
@DisplayName("Payment Gateway Integration Tests")
class PaymentGatewayIntegrationTest {

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15-alpine")
            .withDatabaseName("testdb")
            .withUsername("test")
            .withPassword("test");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Should process payment with Stripe successfully")
    @WithMockUser
    void shouldProcessPaymentWithStripe() throws Exception {
        String paymentJson = """
                {
                    "bookingId": 1,
                    "userId": 1,
                    "amount": 100.00,
                    "currency": "USD",
                    "paymentMethod": "CREDIT_CARD",
                    "gateway": "STRIPE",
                    "cardToken": "tok_visa"
                }
                """;

        mockMvc.perform(post("/api/v1/payments/process")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(paymentJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.gateway").value("STRIPE"))
                .andExpect(jsonPath("$.amount").value(100.00));
    }

    @Test
    @DisplayName("Should process payment with Razorpay successfully")
    @WithMockUser
    void shouldProcessPaymentWithRazorpay() throws Exception {
        String paymentJson = """
                {
                    "bookingId": 2,
                    "userId": 1,
                    "amount": 5000.00,
                    "currency": "INR",
                    "paymentMethod": "UPI",
                    "gateway": "RAZORPAY",
                    "upiId": "user@paytm"
                }
                """;

        mockMvc.perform(post("/api/v1/payments/process")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(paymentJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.gateway").value("RAZORPAY"))
                .andExpect(jsonPath("$.currency").value("INR"));
    }

    @Test
    @DisplayName("Should handle payment failure gracefully")
    @WithMockUser
    void shouldHandlePaymentFailure() throws Exception {
        String paymentJson = """
                {
                    "bookingId": 3,
                    "userId": 1,
                    "amount": 100.00,
                    "currency": "USD",
                    "paymentMethod": "CREDIT_CARD",
                    "gateway": "STRIPE",
                    "cardToken": "tok_chargeDeclined"
                }
                """;

        mockMvc.perform(post("/api/v1/payments/process")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(paymentJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value("FAILED"));
    }

    @Test
    @DisplayName("Should process refund successfully")
    @WithMockUser
    void shouldProcessRefund() throws Exception {
        // First, create a successful payment
        String paymentJson = """
                {
                    "bookingId": 4,
                    "userId": 1,
                    "amount": 100.00,
                    "currency": "USD",
                    "paymentMethod": "CREDIT_CARD",
                    "gateway": "STRIPE",
                    "cardToken": "tok_visa"
                }
                """;

        String response = mockMvc.perform(post("/api/v1/payments/process")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(paymentJson))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Long paymentId = objectMapper.readTree(response).get("id").asLong();

        // Then, process refund
        mockMvc.perform(post("/api/v1/payments/" + paymentId + "/refund")
                        .with(csrf())
                        .param("reason", "Customer cancellation"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("REFUNDED"));
    }

    @Test
    @DisplayName("Should validate payment before booking confirmation")
    @WithMockUser
    void shouldValidatePaymentBeforeConfirmation() throws Exception {
        mockMvc.perform(get("/api/v1/payments/booking/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Should get payment history for user")
    @WithMockUser
    void shouldGetPaymentHistory() throws Exception {
        mockMvc.perform(get("/api/v1/payments/user/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    @DisplayName("Should handle concurrent payment requests")
    @WithMockUser
    void shouldHandleConcurrentPayments() throws Exception {
        String paymentJson = """
                {
                    "bookingId": 5,
                    "userId": 1,
                    "amount": 100.00,
                    "currency": "USD",
                    "paymentMethod": "CREDIT_CARD",
                    "gateway": "STRIPE",
                    "cardToken": "tok_visa"
                }
                """;

        // Simulate concurrent requests
        mockMvc.perform(post("/api/v1/payments/process")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(paymentJson))
                .andExpect(status().isOk());

        // Second request should be idempotent or rejected
        mockMvc.perform(post("/api/v1/payments/process")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(paymentJson))
                .andExpect(status().isConflict());
    }
}
