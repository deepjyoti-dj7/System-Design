package com.airline.management.dto.airline;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AirlineRequest {

    @NotBlank(message = "Airline name is required")
    private String name;

    @NotBlank(message = "Airline code is required")
    private String code; // e.g., "AI" for Air India
}
