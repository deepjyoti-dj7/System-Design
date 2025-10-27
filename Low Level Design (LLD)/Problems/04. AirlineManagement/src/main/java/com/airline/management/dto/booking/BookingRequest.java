package com.airline.management.dto.booking;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class BookingRequest {

    @NotNull
    private Long flightId;

    @NotNull
    private Long userId;

    private List<Long> passengerIds; // optional - link existing passengers
}
