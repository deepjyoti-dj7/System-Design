package com.airline.management.service.impl;

import com.airline.management.dto.airline.AirlineRequest;
import com.airline.management.dto.airline.AirlineResponse;
import com.airline.management.entity.Airline;
import com.airline.management.exception.ResourceNotFoundException;
import com.airline.management.exception.ValidationException;
import com.airline.management.mapper.AirlineMapper;
import com.airline.management.repository.AirlineRepository;
import com.airline.management.service.AirlineService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class AirlineServiceImpl implements AirlineService {

    private final AirlineRepository airlineRepository;
    private final AirlineMapper airlineMapper;

    @Override
    public AirlineResponse createAirline(AirlineRequest request) {
        if (airlineRepository.findByCode(request.getCode()).isPresent()) {
            throw new ValidationException("Airline code already exists");
        }

        Airline airline = airlineMapper.toEntity(request);
        airlineRepository.save(airline);
        return airlineMapper.toResponse(airline);
    }

    @Override
    public AirlineResponse getAirlineById(Long id) {
        Airline airline = airlineRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Airline not found"));
        return airlineMapper.toResponse(airline);
    }

    @Override
    public List<AirlineResponse> getAllAirlines() {
        return airlineRepository.findAll().stream()
                .map(airlineMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public AirlineResponse updateAirline(Long id, AirlineRequest request) {
        Airline airline = airlineRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Airline not found"));

        if (request.getCode() != null && !request.getCode().equals(airline.getCode())
                && airlineRepository.findByCode(request.getCode()).isPresent()) {
            throw new ValidationException("Airline code already exists");
        }

        airlineMapper.updateEntity(airline, request);
        airlineRepository.save(airline);
        return airlineMapper.toResponse(airline);
    }

    @Override
    public void deleteAirline(Long id) {
        if (!airlineRepository.existsById(id)) {
            throw new ResourceNotFoundException("Airline not found");
        }
        airlineRepository.deleteById(id);
    }
}
