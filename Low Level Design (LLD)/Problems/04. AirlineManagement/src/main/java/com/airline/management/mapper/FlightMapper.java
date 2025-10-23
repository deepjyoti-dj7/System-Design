package com.airline.management.mapper;

import com.airline.management.dto.flight.FlightRequest;
import com.airline.management.dto.flight.FlightResponse;
import com.airline.management.entity.Airline;
import com.airline.management.entity.Airport;
import com.airline.management.entity.Flight;
import org.springframework.stereotype.Component;

@Component
public class FlightMapper {

    public FlightResponse toResponse(Flight flight) {
        return FlightResponse.builder()
                .id(flight.getId())
                .flightNumber(flight.getFlightNumber())
                .airlineId(flight.getAirline() != null ? flight.getAirline().getId() : null)
                .airlineName(flight.getAirline() != null ? flight.getAirline().getName() : null)
                .departureAirportId(flight.getDepartureAirport() != null ? flight.getDepartureAirport().getId() : null)
                .departureAirportName(flight.getDepartureAirport() != null ? flight.getDepartureAirport().getName() : null)
                .arrivalAirportId(flight.getArrivalAirport() != null ? flight.getArrivalAirport().getId() : null)
                .arrivalAirportName(flight.getArrivalAirport() != null ? flight.getArrivalAirport().getName() : null)
                .departureTime(flight.getDepartureTime())
                .arrivalTime(flight.getArrivalTime())
                .totalSeats(flight.getTotalSeats())
                .ticketPrice(flight.getTicketPrice())
                .build();
    }

    public Flight toEntity(FlightRequest request, Airline airline, Airport departure, Airport arrival) {
        return Flight.builder()
                .flightNumber(request.getFlightNumber())
                .airline(airline)
                .departureAirport(departure)
                .arrivalAirport(arrival)
                .departureTime(request.getDepartureTime())
                .arrivalTime(request.getArrivalTime())
                .totalSeats(request.getTotalSeats())
                .ticketPrice(request.getTicketPrice())
                .build();
    }

    public void updateEntity(Flight flight, FlightRequest request, Airline airline, Airport departure, Airport arrival) {
        if (request.getFlightNumber() != null) flight.setFlightNumber(request.getFlightNumber());
        if (airline != null) flight.setAirline(airline);
        if (departure != null) flight.setDepartureAirport(departure);
        if (arrival != null) flight.setArrivalAirport(arrival);
        if (request.getDepartureTime() != null) flight.setDepartureTime(request.getDepartureTime());
        if (request.getArrivalTime() != null) flight.setArrivalTime(request.getArrivalTime());
        if (request.getTotalSeats() > 0) flight.setTotalSeats(request.getTotalSeats());
        if (request.getTicketPrice() >= 0) flight.setTicketPrice(request.getTicketPrice());
    }
}
