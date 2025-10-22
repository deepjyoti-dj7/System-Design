package com.airline.management.service;

import com.airline.management.dto.airline.AirlineRequest;
import com.airline.management.dto.airline.AirlineResponse;

import java.util.List;

public interface AirlineService {

    AirlineResponse createAirline(AirlineRequest request);
    AirlineResponse getAirlineById(Long id);
    List<AirlineResponse> getAllAirlines();
    AirlineResponse updateAirline(Long id, AirlineRequest request);
    void deleteAirline(Long id);
}
