package com.airline.management.service;

import com.airline.management.dto.flight.FlightRequest;
import com.airline.management.dto.flight.FlightResponse;

import java.util.List;

public interface FlightService {
    FlightResponse createFlight(FlightRequest request);
    FlightResponse updateFlight(Long id, FlightRequest request);
    FlightResponse getFlightById(Long id);
    List<FlightResponse> getAllFlights();
    void deleteFlight(Long id);
}
