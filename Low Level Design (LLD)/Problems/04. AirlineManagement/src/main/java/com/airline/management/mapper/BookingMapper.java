package com.airline.management.mapper;

import com.airline.management.dto.booking.BookingRequest;
import com.airline.management.dto.booking.BookingResponse;
import com.airline.management.dto.passenger.PassengerResponse;
import com.airline.management.dto.payment.PaymentResponse;
import com.airline.management.entity.Booking;
import com.airline.management.entity.Flight;
import com.airline.management.entity.User;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class BookingMapper {

    private final PassengerMapper passengerMapper;
    private final PaymentMapper paymentMapper;

    public BookingMapper(PassengerMapper passengerMapper, PaymentMapper paymentMapper) {
        this.passengerMapper = passengerMapper;
        this.paymentMapper = paymentMapper;
    }

    public Booking toEntity(BookingRequest request, Flight flight, User user) {
        return Booking.builder()
                .flight(flight)
                .user(user)
                .bookingTime(LocalDateTime.now())
                .build();
    }

    public BookingResponse toResponse(Booking booking) {
        List<PassengerResponse> passengers = booking.getPassengers() != null
                ? booking.getPassengers().stream().map(passengerMapper::toResponse).collect(Collectors.toList())
                : List.of();

        PaymentResponse payment = booking.getPayment() != null
                ? paymentMapper.toResponse(booking.getPayment())
                : null;

        return BookingResponse.builder()
                .id(booking.getId())
                .flightId(booking.getFlight().getId())
                .flightNumber(booking.getFlight().getFlightNumber())
                .userId(booking.getUser().getId())
                .username(booking.getUser().getUsername())
                .bookingTime(booking.getBookingTime())
                .passengers(passengers)
                .payment(payment)
                .build();
    }
}
