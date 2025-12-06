package com.urbanclone.booking.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.urbanclone.booking.dto.BookingDto;
import com.urbanclone.booking.dto.BookingRequest;
import com.urbanclone.booking.entity.BookingStatus;
import com.urbanclone.booking.service.BookingService;
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
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookingController.class)
@DisplayName("Booking Controller Tests")
class BookingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private BookingService bookingService;

    private BookingDto testBookingDto;
    private BookingRequest bookingRequest;

    @BeforeEach
    void setUp() {
        testBookingDto = BookingDto.builder()
                .id(1L)
                .userId(100L)
                .serviceId(200L)
                .partnerId(300L)
                .status(BookingStatus.PENDING)
                .scheduledTime(LocalDateTime.now().plusDays(1))
                .address("123 Test St")
                .latitude(40.7128)
                .longitude(-74.0060)
                .estimatedPrice(BigDecimal.valueOf(50.00))
                .actualPrice(BigDecimal.valueOf(50.00))
                .createdAt(LocalDateTime.now())
                .build();

        bookingRequest = BookingRequest.builder()
                .userId(100L)
                .serviceId(200L)
                .scheduledTime(LocalDateTime.now().plusDays(1))
                .address("123 Test St")
                .latitude(40.7128)
                .longitude(-74.0060)
                .notes("Test booking")
                .build();
    }

    @Test
    @DisplayName("Should create booking successfully")
    void shouldCreateBooking() throws Exception {
        // Given
        when(bookingService.createBooking(any(BookingRequest.class)))
                .thenReturn(testBookingDto);

        // When & Then
        mockMvc.perform(post("/api/v1/bookings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookingRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.userId").value(100))
                .andExpect(jsonPath("$.status").value("PENDING"));
    }

    @Test
    @DisplayName("Should get booking by id successfully")
    void shouldGetBookingById() throws Exception {
        // Given
        when(bookingService.getBookingById(1L)).thenReturn(testBookingDto);

        // When & Then
        mockMvc.perform(get("/api/v1/bookings/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.status").value("PENDING"));
    }

    @Test
    @DisplayName("Should get bookings by user id")
    void shouldGetBookingsByUserId() throws Exception {
        // Given
        List<BookingDto> bookings = Arrays.asList(testBookingDto);
        when(bookingService.getBookingsByUserId(100L)).thenReturn(bookings);

        // When & Then
        mockMvc.perform(get("/api/v1/bookings/user/100"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].userId").value(100));
    }

    @Test
    @DisplayName("Should confirm booking successfully")
    void shouldConfirmBooking() throws Exception {
        // Given
        testBookingDto.setStatus(BookingStatus.CONFIRMED);
        when(bookingService.confirmBooking(1L)).thenReturn(testBookingDto);

        // When & Then
        mockMvc.perform(post("/api/v1/bookings/1/confirm"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("CONFIRMED"));
    }

    @Test
    @DisplayName("Should start booking successfully")
    void shouldStartBooking() throws Exception {
        // Given
        testBookingDto.setStatus(BookingStatus.IN_PROGRESS);
        when(bookingService.startBooking(1L)).thenReturn(testBookingDto);

        // When & Then
        mockMvc.perform(post("/api/v1/bookings/1/start"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("IN_PROGRESS"));
    }

    @Test
    @DisplayName("Should complete booking successfully")
    void shouldCompleteBooking() throws Exception {
        // Given
        testBookingDto.setStatus(BookingStatus.COMPLETED);
        when(bookingService.completeBooking(1L)).thenReturn(testBookingDto);

        // When & Then
        mockMvc.perform(post("/api/v1/bookings/1/complete"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("COMPLETED"));
    }

    @Test
    @DisplayName("Should cancel booking successfully")
    void shouldCancelBooking() throws Exception {
        // Given
        testBookingDto.setStatus(BookingStatus.CANCELLED);
        when(bookingService.cancelBooking(eq(1L), any(String.class))).thenReturn(testBookingDto);

        // When & Then
        mockMvc.perform(post("/api/v1/bookings/1/cancel")
                        .param("reason", "Customer request"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("CANCELLED"));
    }
}
