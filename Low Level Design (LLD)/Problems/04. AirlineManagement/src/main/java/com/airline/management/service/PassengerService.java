package com.airline.management.service;

import com.airline.management.dto.passenger.PassengerRequest;
import com.airline.management.dto.passenger.PassengerResponse;

import java.util.List;

public interface PassengerService {

    PassengerResponse createPassenger(Long bookingId, PassengerRequest request);
    PassengerResponse getPassengerById(Long id);
    List<PassengerResponse> getAllPassengers();
    PassengerResponse updatePassenger(Long id, PassengerRequest request);
    void deletePassenger(Long id);
}
