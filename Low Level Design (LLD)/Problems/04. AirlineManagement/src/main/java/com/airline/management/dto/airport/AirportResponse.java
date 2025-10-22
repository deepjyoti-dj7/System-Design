package com.airline.management.dto.airport;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AirportResponse {
    private Long id;
    private String name;
    private String code;
    private String city;
    private String country;
}
