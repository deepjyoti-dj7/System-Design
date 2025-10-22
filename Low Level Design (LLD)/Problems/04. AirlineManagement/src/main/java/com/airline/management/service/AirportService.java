package com.airline.management.service;

import com.airline.management.entity.Airport;

import java.util.List;

public interface AirportService {
    Airport createAirport(Airport airport);
    Airport getAirportById(Long id);
    List<Airport> getAllAirports();
    Airport updateAirport(Long id, Airport airport);
    void deleteAirport(Long id);
}
