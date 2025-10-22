package com.airline.management.service.impl;

import com.airline.management.dto.passenger.PassengerRequest;
import com.airline.management.dto.passenger.PassengerResponse;
import com.airline.management.entity.Booking;
import com.airline.management.entity.Passenger;
import com.airline.management.exception.ResourceNotFoundException;
import com.airline.management.mapper.PassengerMapper;
import com.airline.management.repository.BookingRepository;
import com.airline.management.repository.PassengerRepository;
import com.airline.management.service.PassengerService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class PassengerServiceImpl implements PassengerService {

    private final PassengerRepository passengerRepository;
    private final BookingRepository bookingRepository;
    private final PassengerMapper passengerMapper;

    @Override
    public PassengerResponse createPassenger(Long bookingId, PassengerRequest request) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with id " + bookingId));

        Passenger passenger = passengerMapper.toEntity(request);
        passenger.setBooking(booking);

        passengerRepository.save(passenger);
        return passengerMapper.toResponse(passenger);
    }

    @Override
    public PassengerResponse getPassengerById(Long id) {
        Passenger passenger = passengerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Passenger not found with id " + id));
        return passengerMapper.toResponse(passenger);
    }

    @Override
    public List<PassengerResponse> getAllPassengers() {
        return passengerRepository.findAll().stream()
                .map(passengerMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public PassengerResponse updatePassenger(Long id, PassengerRequest request) {
        Passenger passenger = passengerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Passenger not found with id " + id));

        passengerMapper.updateEntity(passenger, request);
        passengerRepository.save(passenger);
        return passengerMapper.toResponse(passenger);
    }

    @Override
    public void deletePassenger(Long id) {
        if (!passengerRepository.existsById(id)) {
            throw new ResourceNotFoundException("Passenger not found with id " + id);
        }
        passengerRepository.deleteById(id);
    }
}
