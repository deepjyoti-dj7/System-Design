package com.urbanclone.integration;

import com.urbanclone.booking.dto.BookingDto;
import com.urbanclone.booking.dto.CreateBookingRequest;
import com.urbanclone.booking.entity.BookingStatus;
import com.urbanclone.catalog.entity.ServiceStatus;
import com.urbanclone.partner.entity.PartnerStatus;
import com.urbanclone.payment.dto.PaymentDto;
import com.urbanclone.payment.entity.PaymentStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.RabbitMQContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("Booking Flow Integration Tests")
class BookingFlowIntegrationTest {

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15-alpine")
            .withDatabaseName("testdb")
            .withUsername("test")
            .withPassword("test");

    @Container
    static RabbitMQContainer rabbitmq = new RabbitMQContainer("rabbitmq:3.12-management");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
        registry.add("spring.rabbitmq.host", rabbitmq::getHost);
        registry.add("spring.rabbitmq.port", rabbitmq::getAmqpPort);
    }

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private static Long createdBookingId;
    private static Long serviceId;
    private static Long partnerId;
    private static Long userId;

    @Test
    @Order(1)
    @DisplayName("1. User should register successfully")
    @WithMockUser
    void shouldRegisterUser() throws Exception {
        String userJson = """
                {
                    "email": "testuser@example.com",
                    "password": "SecurePass123!",
                    "firstName": "John",
                    "lastName": "Doe",
                    "phoneNumber": "+1234567890"
                }
                """;

        String response = mockMvc.perform(post("/api/v1/users/register")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.email").value("testuser@example.com"))
                .andReturn()
                .getResponse()
                .getContentAsString();

        userId = objectMapper.readTree(response).get("id").asLong();
    }

    @Test
    @Order(2)
    @DisplayName("2. Service should be available in catalog")
    @WithMockUser(roles = "ADMIN")
    void shouldCreateService() throws Exception {
        String serviceJson = """
                {
                    "name": "Deep Cleaning",
                    "description": "Complete deep cleaning service",
                    "categoryId": 1,
                    "basePrice": 100.00,
                    "currency": "USD",
                    "durationMinutes": 120,
                    "status": "ACTIVE"
                }
                """;

        String response = mockMvc.perform(post("/api/v1/services")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(serviceJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Deep Cleaning"))
                .andReturn()
                .getResponse()
                .getContentAsString();

        serviceId = objectMapper.readTree(response).get("id").asLong();
    }

    @Test
    @Order(3)
    @DisplayName("3. Partner should register and be verified")
    @WithMockUser
    void shouldRegisterAndVerifyPartner() throws Exception {
        String partnerJson = """
                {
                    "userId": %d,
                    "businessName": "Pro Cleaners",
                    "email": "partner@procleaners.com",
                    "phoneNumber": "+9876543210",
                    "latitude": 40.7128,
                    "longitude": -74.0060,
                    "serviceIds": [%d]
                }
                """.formatted(userId, serviceId);

        String response = mockMvc.perform(post("/api/v1/partners/register")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(partnerJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.businessName").value("Pro Cleaners"))
                .andReturn()
                .getResponse()
                .getContentAsString();

        partnerId = objectMapper.readTree(response).get("id").asLong();

        // Verify partner
        mockMvc.perform(put("/api/v1/partners/" + partnerId + "/verify")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("ACTIVE"));
    }

    @Test
    @Order(4)
    @DisplayName("4. User should create booking successfully")
    @WithMockUser
    void shouldCreateBooking() throws Exception {
        CreateBookingRequest bookingRequest = CreateBookingRequest.builder()
                .userId(userId)
                .serviceId(serviceId)
                .scheduledDateTime(LocalDateTime.now().plusDays(1))
                .addressLine1("123 Main St")
                .city("New York")
                .state("NY")
                .zipCode("10001")
                .latitude(40.7128)
                .longitude(-74.0060)
                .notes("Please be on time")
                .build();

        String response = mockMvc.perform(post("/api/v1/bookings")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookingRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.userId").value(userId))
                .andExpect(jsonPath("$.status").value("PENDING"))
                .andReturn()
                .getResponse()
                .getContentAsString();

        BookingDto bookingDto = objectMapper.readValue(response, BookingDto.class);
        createdBookingId = bookingDto.getId();
    }

    @Test
    @Order(5)
    @DisplayName("5. System should assign partner to booking")
    @WithMockUser
    void shouldAssignPartnerToBooking() throws Exception {
        mockMvc.perform(put("/api/v1/bookings/" + createdBookingId + "/assign/" + partnerId)
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.partnerId").value(partnerId))
                .andExpect(jsonPath("$.status").value("CONFIRMED"));
    }

    @Test
    @Order(6)
    @DisplayName("6. User should process payment for booking")
    @WithMockUser
    void shouldProcessPayment() throws Exception {
        String paymentJson = """
                {
                    "bookingId": %d,
                    "userId": %d,
                    "amount": 100.00,
                    "currency": "USD",
                    "paymentMethod": "CREDIT_CARD",
                    "gateway": "STRIPE"
                }
                """.formatted(createdBookingId, userId);

        mockMvc.perform(post("/api/v1/payments/process")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(paymentJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("COMPLETED"))
                .andExpect(jsonPath("$.bookingId").value(createdBookingId));
    }

    @Test
    @Order(7)
    @DisplayName("7. Partner should start service")
    @WithMockUser
    void shouldStartService() throws Exception {
        mockMvc.perform(put("/api/v1/bookings/" + createdBookingId + "/status")
                        .with(csrf())
                        .param("status", "IN_PROGRESS"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("IN_PROGRESS"));
    }

    @Test
    @Order(8)
    @DisplayName("8. Partner should complete service")
    @WithMockUser
    void shouldCompleteService() throws Exception {
        mockMvc.perform(put("/api/v1/bookings/" + createdBookingId + "/status")
                        .with(csrf())
                        .param("status", "COMPLETED"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("COMPLETED"));
    }

    @Test
    @Order(9)
    @DisplayName("9. User should be able to rate the service")
    @WithMockUser
    void shouldRateService() throws Exception {
        String ratingJson = """
                {
                    "bookingId": %d,
                    "userId": %d,
                    "partnerId": %d,
                    "rating": 5,
                    "comment": "Excellent service!"
                }
                """.formatted(createdBookingId, userId, partnerId);

        mockMvc.perform(post("/api/v1/ratings")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(ratingJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.rating").value(5));
    }

    @Test
    @Order(10)
    @DisplayName("10. User should view booking history")
    @WithMockUser
    void shouldGetBookingHistory() throws Exception {
        mockMvc.perform(get("/api/v1/bookings/user/" + userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").value(createdBookingId));
    }
}
