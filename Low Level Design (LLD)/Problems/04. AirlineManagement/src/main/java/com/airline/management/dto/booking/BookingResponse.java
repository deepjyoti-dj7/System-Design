package com.airline.management.dto.booking;

import com.airline.management.dto.passenger.PassengerResponse;
import com.airline.management.dto.payment.PaymentResponse;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class BookingResponse {
    private Long id;
    private Long flightId;
    private String flightNumber;
    private Long userId;
    private String username;
    private LocalDateTime bookingTime;
    private List<PassengerResponse> passengers;
    private PaymentResponse payment;
}
