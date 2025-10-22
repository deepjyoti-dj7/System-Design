package com.airline.management.service.impl;

import com.airline.management.dto.airport.AirportRequest;
import com.airline.management.dto.airport.AirportResponse;
import com.airline.management.entity.Airport;
import com.airline.management.exception.ResourceNotFoundException;
import com.airline.management.mapper.AirportMapper;
import com.airline.management.repository.AirportRepository;
import com.airline.management.service.AirportService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AirportServiceImpl implements AirportService {

    private final AirportRepository airportRepository;
    private final AirportMapper airportMapper;

    @Override
    public AirportResponse createAirport(AirportRequest request) {
        Airport airport = airportMapper.toEntity(request);
        airportRepository.save(airport);
        return airportMapper.toResponse(airport);
    }

    @Override
    public AirportResponse getAirportById(Long id) {
        Airport airport = airportRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Airport not found with id: " + id));
        return airportMapper.toResponse(airport);
    }

    @Override
    public List<AirportResponse> getAllAirports() {
        return airportRepository.findAll().stream()
                .map(airportMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public AirportResponse updateAirport(Long id, AirportRequest request) {
        Airport existing = airportRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Airport not found"));
        existing.setName(request.getName());
        existing.setCode(request.getCode());
        existing.setCity(request.getCity());
        existing.setCountry(request.getCountry());
        airportRepository.save(existing);
        return airportMapper.toResponse(existing);
    }

    @Override
    public void deleteAirport(Long id) {
        Airport airport = airportRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Airport not found"));
        airportRepository.delete(airport);
    }
}
