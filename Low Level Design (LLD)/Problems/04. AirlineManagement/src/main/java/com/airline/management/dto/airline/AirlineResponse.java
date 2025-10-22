package com.airline.management.dto.airline;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AirlineResponse {
    private Long id;
    private String name;
    private String code;
}
