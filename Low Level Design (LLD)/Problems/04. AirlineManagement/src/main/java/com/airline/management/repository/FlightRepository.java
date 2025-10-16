package com.airline.management.repository;

import com.airline.management.entity.Flight;
import com.airline.management.entity.Airport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {

    List<Flight> findByDepartureAirportAndArrivalAirportAndDepartureTimeBetween(
            Airport departure, Airport arrival, LocalDateTime start, LocalDateTime end);

    List<Flight> findByAirlineId(Long airlineId);
}
