package com.airline.management.controller;

import com.airline.management.dto.flight.FlightRequest;
import com.airline.management.dto.flight.FlightResponse;
import com.airline.management.dto.util.ApiResponse;
import com.airline.management.service.FlightService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/flights")
@RequiredArgsConstructor
public class FlightController {

    private final FlightService flightService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<FlightResponse>> createFlight(@Valid @RequestBody FlightRequest request) {
        FlightResponse created = flightService.createFlight(request);
        return ResponseEntity.ok(ApiResponse.ok("Flight created", created));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<FlightResponse>> getFlight(@PathVariable Long id) {
        FlightResponse response = flightService.getFlightById(id);
        return ResponseEntity.ok(ApiResponse.ok(response));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<FlightResponse>>> getAllFlights() {
        List<FlightResponse> flights = flightService.getAllFlights();
        return ResponseEntity.ok(ApiResponse.ok(flights));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<FlightResponse>> updateFlight(@PathVariable Long id,
                                                                    @Valid @RequestBody FlightRequest request) {
        FlightResponse updated = flightService.updateFlight(id, request);
        return ResponseEntity.ok(ApiResponse.ok("Flight updated", updated));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> deleteFlight(@PathVariable Long id) {
        flightService.deleteFlight(id);
        return ResponseEntity.ok(ApiResponse.ok("Flight deleted", null));
    }
}
