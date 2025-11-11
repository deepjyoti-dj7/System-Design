package com.airline.management.event.model;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingCreatedEvent {
    private Long bookingId;
    private Long flightId;
    private Long userId;
    private double amount;
    private LocalDateTime bookingTime;
}
