package com.airline.management.service;

import com.airline.management.dto.airport.AirportRequest;
import com.airline.management.dto.airport.AirportResponse;
import com.airline.management.entity.Airport;

import java.util.List;

public interface AirportService {
    AirportResponse createAirport(AirportRequest request);
    AirportResponse getAirportById(Long id);
    List<AirportResponse> getAllAirports();
    AirportResponse updateAirport(Long id, AirportRequest request);
    void deleteAirport(Long id);
}
