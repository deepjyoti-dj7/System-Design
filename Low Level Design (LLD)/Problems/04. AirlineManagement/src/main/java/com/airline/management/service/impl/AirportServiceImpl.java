package com.airline.management.service.impl;

import com.airline.management.dto.airport.AirportRequest;
import com.airline.management.dto.airport.AirportResponse;
import com.airline.management.entity.Airport;
import com.airline.management.exception.ResourceNotFoundException;
import com.airline.management.exception.ValidationException;
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
        if (airportRepository.findByCode(request.getCode()).isPresent()) {
            throw new ValidationException("Airport with code '" + request.getCode() + "' already exists");
        }

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
        return airportRepository.findAll()
                .stream()
                .map(airportMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public AirportResponse updateAirport(Long id, AirportRequest request) {
        Airport airport = airportRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Airport not found with id: " + id));

        if (request.getCode() != null && !request.getCode().equals(airport.getCode())
                && airportRepository.findByCode(request.getCode()).isPresent()) {
            throw new ValidationException("Airport code '" + request.getCode() + "' already in use");
        }

        if (request.getName() != null) airport.setName(request.getName());
        if (request.getCode() != null) airport.setCode(request.getCode());
        if (request.getCity() != null) airport.setCity(request.getCity());
        if (request.getCountry() != null) airport.setCountry(request.getCountry());

        airportRepository.save(airport);
        return airportMapper.toResponse(airport);
    }

    @Override
    public void deleteAirport(Long id) {
        if (!airportRepository.existsById(id)) {
            throw new ResourceNotFoundException("Airport not found with id: " + id);
        }
        airportRepository.deleteById(id);
    }
}
