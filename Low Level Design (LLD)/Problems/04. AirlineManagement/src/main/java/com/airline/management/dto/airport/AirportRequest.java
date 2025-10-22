package com.airline.management.dto.airport;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AirportRequest {

    @NotBlank(message = "Airport name is required")
    private String name;

    @NotBlank(message = "Airport code is required")
    private String code;

    @NotBlank(message = "City is required")
    private String city;

    @NotBlank(message = "Country is required")
    private String country;
}
