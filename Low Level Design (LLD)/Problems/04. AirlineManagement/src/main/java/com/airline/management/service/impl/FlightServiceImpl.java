package com.airline.management.service.impl;

import com.airline.management.dto.flight.FlightRequest;
import com.airline.management.dto.flight.FlightResponse;
import com.airline.management.entity.Airline;
import com.airline.management.entity.Airport;
import com.airline.management.entity.Flight;
import com.airline.management.exception.ResourceNotFoundException;
import com.airline.management.mapper.FlightMapper;
import com.airline.management.repository.AirlineRepository;
import com.airline.management.repository.AirportRepository;
import com.airline.management.repository.FlightRepository;
import com.airline.management.service.FlightService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class FlightServiceImpl implements FlightService {

    private final FlightRepository flightRepository;
    private final AirlineRepository airlineRepository;
    private final AirportRepository airportRepository;
    private final FlightMapper flightMapper;

    @Override
    public FlightResponse createFlight(FlightRequest request) {
        Airline airline = airlineRepository.findById(request.getAirlineId())
                .orElseThrow(() -> new ResourceNotFoundException("Airline not found with id " + request.getAirlineId()));

        Airport departure = airportRepository.findById(request.getDepartureAirportId())
                .orElseThrow(() -> new ResourceNotFoundException("Departure airport not found with id " + request.getDepartureAirportId()));

        Airport arrival = airportRepository.findById(request.getArrivalAirportId())
                .orElseThrow(() -> new ResourceNotFoundException("Arrival airport not found with id " + request.getArrivalAirportId()));

        Flight flight = flightMapper.toEntity(request, airline, departure, arrival);
        flightRepository.save(flight);
        return flightMapper.toResponse(flight);
    }

    @Override
    public FlightResponse updateFlight(Long id, FlightRequest request) {
        Flight flight = flightRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Flight not found with id " + id));

        Airline airline = airlineRepository.findById(request.getAirlineId())
                .orElseThrow(() -> new ResourceNotFoundException("Airline not found with id " + request.getAirlineId()));

        Airport departure = airportRepository.findById(request.getDepartureAirportId())
                .orElseThrow(() -> new ResourceNotFoundException("Departure airport not found with id " + request.getDepartureAirportId()));

        Airport arrival = airportRepository.findById(request.getArrivalAirportId())
                .orElseThrow(() -> new ResourceNotFoundException("Arrival airport not found with id " + request.getArrivalAirportId()));

        flightMapper.updateEntity(flight, request, airline, departure, arrival);
        flightRepository.save(flight);
        return flightMapper.toResponse(flight);
    }

    @Override
    public FlightResponse getFlightById(Long id) {
        Flight flight = flightRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Flight not found with id " + id));
        return flightMapper.toResponse(flight);
    }

    @Override
    public List<FlightResponse> getAllFlights() {
        return flightRepository.findAll().stream()
                .map(flightMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteFlight(Long id) {
        if (!flightRepository.existsById(id)) {
            throw new ResourceNotFoundException("Flight not found with id " + id);
        }
        flightRepository.deleteById(id);
    }
}
