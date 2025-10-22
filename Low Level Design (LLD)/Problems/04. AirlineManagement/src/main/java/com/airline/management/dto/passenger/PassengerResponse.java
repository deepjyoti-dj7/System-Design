package com.airline.management.dto.passenger;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PassengerResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private Long bookingId; // to link passenger to a booking
}
