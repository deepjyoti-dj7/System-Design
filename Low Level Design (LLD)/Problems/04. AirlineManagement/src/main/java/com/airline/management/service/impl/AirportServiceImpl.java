package com.airline.management.service.impl;

import com.airline.management.entity.Airport;
import com.airline.management.exception.ResourceNotFoundException;
import com.airline.management.repository.AirportRepository;
import com.airline.management.service.AirportService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AirportServiceImpl implements AirportService {

    private final AirportRepository airportRepository;

    @Override
    public Airport createAirport(Airport airport) {
        return airportRepository.save(airport);
    }

    @Override
    public Airport getAirportById(Long id) {
        return airportRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Airport not found with id: " + id));
    }

    @Override
    public List<Airport> getAllAirports() {
        return airportRepository.findAll();
    }

    @Override
    public Airport updateAirport(Long id, Airport airport) {
        Airport existing = getAirportById(id);
        existing.setName(airport.getName());
        existing.setCode(airport.getCode());
        existing.setCity(airport.getCity());
        existing.setCountry(airport.getCountry());
        return airportRepository.save(existing);
    }

    @Override
    public void deleteAirport(Long id) {
        Airport airport = getAirportById(id);
        airportRepository.delete(airport);
    }
}
