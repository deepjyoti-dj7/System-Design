package com.airline.management.mapper;

import com.airline.management.dto.airline.AirlineRequest;
import com.airline.management.dto.airline.AirlineResponse;
import com.airline.management.entity.Airline;
import org.springframework.stereotype.Component;

@Component
public class AirlineMapper {

    public AirlineResponse toResponse(Airline airline) {
        return AirlineResponse.builder()
                .id(airline.getId())
                .name(airline.getName())
                .code(airline.getCode())
                .build();
    }

    public Airline toEntity(AirlineRequest request) {
        return Airline.builder()
                .name(request.getName())
                .code(request.getCode())
                .build();
    }

    public void updateEntity(Airline airline, AirlineRequest request) {
        if (request.getName() != null && !request.getName().isBlank()) {
            airline.setName(request.getName());
        }
        if (request.getCode() != null && !request.getCode().isBlank()) {
            airline.setCode(request.getCode());
        }
    }
}
