package com.airline.management.mapper;

import com.airline.management.dto.airport.AirportRequest;
import com.airline.management.dto.airport.AirportResponse;
import com.airline.management.entity.Airport;
import org.springframework.stereotype.Component;

@Component
public class AirportMapper {
    public AirportResponse toResponse(Airport airport) {
        return AirportResponse.builder()
                .id(airport.getId())
                .name(airport.getName())
                .code(airport.getCode())
                .city(airport.getCity())
                .country(airport.getCountry())
                .build();
    }

    public Airport toEntity(AirportRequest request) {
        return Airport.builder()
                .name(request.getName())
                .code(request.getCode())
                .city(request.getCity())
                .country(request.getCountry())
                .build();
    }
}
