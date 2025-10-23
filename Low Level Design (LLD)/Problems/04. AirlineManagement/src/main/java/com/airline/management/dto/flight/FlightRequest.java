package com.airline.management.dto.flight;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FlightRequest {

    @NotBlank
    private String flightNumber;

    @NotNull
    private Long airlineId;

    @NotNull
    private Long departureAirportId;

    @NotNull
    private Long arrivalAirportId;

    @NotNull
    private LocalDateTime departureTime;

    @NotNull
    private LocalDateTime arrivalTime;

    @Min(1)
    private int totalSeats;

    @Min(0)
    private double ticketPrice;
}
