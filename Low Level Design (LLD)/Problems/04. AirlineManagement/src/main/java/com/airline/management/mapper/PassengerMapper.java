package com.airline.management.mapper;

import com.airline.management.dto.passenger.PassengerRequest;
import com.airline.management.dto.passenger.PassengerResponse;
import com.airline.management.entity.Passenger;
import org.springframework.stereotype.Component;

@Component
public class PassengerMapper {

    public PassengerResponse toResponse(Passenger passenger) {
        return PassengerResponse.builder()
                .id(passenger.getId())
                .firstName(passenger.getFirstName())
                .lastName(passenger.getLastName())
                .email(passenger.getEmail())
                .phone(passenger.getPhone())
                .bookingId(passenger.getBooking() != null ? passenger.getBooking().getId() : null)
                .build();
    }

    public Passenger toEntity(PassengerRequest request) {
        return Passenger.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .phone(request.getPhone())
                .build();
    }

    public void updateEntity(Passenger passenger, PassengerRequest request) {
        if (request.getFirstName() != null) passenger.setFirstName(request.getFirstName());
        if (request.getLastName() != null) passenger.setLastName(request.getLastName());
        if (request.getEmail() != null) passenger.setEmail(request.getEmail());
        if (request.getPhone() != null) passenger.setPhone(request.getPhone());
    }
}
