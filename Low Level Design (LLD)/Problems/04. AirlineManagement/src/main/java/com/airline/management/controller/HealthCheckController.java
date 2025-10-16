package com.airline.management.controller;

import com.airline.management.dto.response.HealthResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@RestController
public class HealthCheckController {

    @GetMapping("/health")
    public HealthResponse healthCheck() {
        // Format timestamp in human-readable form
        String formattedTime = DateTimeFormatter.ofPattern("dd MMMM yyyy 'at' HH:mm z")
                .withZone(ZoneId.of("Asia/Kolkata"))  // IST timezone
                .format(Instant.now());

        return new HealthResponse("Health is OK!", "Airline API", formattedTime);
    }
}
