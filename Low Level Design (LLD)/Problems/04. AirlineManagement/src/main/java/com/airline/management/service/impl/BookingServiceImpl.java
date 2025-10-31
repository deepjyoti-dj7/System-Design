package com.airline.management.service.impl;

import com.airline.management.dto.booking.BookingRequest;
import com.airline.management.dto.booking.BookingResponse;
import com.airline.management.entity.Booking;
import com.airline.management.entity.Flight;
import com.airline.management.entity.Passenger;
import com.airline.management.entity.User;
import com.airline.management.exception.ResourceNotFoundException;
import com.airline.management.mapper.BookingMapper;
import com.airline.management.repository.*;
import com.airline.management.service.BookingService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final FlightRepository flightRepository;
    private final UserRepository userRepository;
    private final PassengerRepository passengerRepository;
    private final BookingMapper bookingMapper;

    @Override
    public BookingResponse createBooking(BookingRequest request) {
        Flight flight = flightRepository.findById(request.getFlightId())
                .orElseThrow(() -> new ResourceNotFoundException("Flight not found with id " + request.getFlightId()));

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + request.getUserId()));

        Booking booking = bookingMapper.toEntity(request, flight, user);
        bookingRepository.save(booking);

        if (request.getPassengerIds() != null && !request.getPassengerIds().isEmpty()) {
            List<Passenger> passengers = passengerRepository.findAllById(request.getPassengerIds());
            passengers.forEach(p -> p.setBooking(booking));
            passengerRepository.saveAll(passengers);
            booking.setPassengers(passengers);
        }

        return bookingMapper.toResponse(booking);
    }

    @Override
    public BookingResponse getBookingById(Long id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with id " + id));
        return bookingMapper.toResponse(booking);
    }

    @Override
    public List<BookingResponse> getAllBookings() {
        return bookingRepository.findAll().stream()
                .map(bookingMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteBooking(Long id) {
        if (!bookingRepository.existsById(id)) {
            throw new ResourceNotFoundException("Booking not found with id " + id);
        }
        bookingRepository.deleteById(id);
    }
}
