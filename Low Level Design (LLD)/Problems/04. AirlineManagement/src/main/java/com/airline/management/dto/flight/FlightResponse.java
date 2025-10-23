package com.airline.management.dto.flight;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class FlightResponse {
    private Long id;
    private String flightNumber;
    private Long airlineId;
    private String airlineName;
    private Long departureAirportId;
    private String departureAirportName;
    private Long arrivalAirportId;
    private String arrivalAirportName;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private int totalSeats;
    private double ticketPrice;
}
