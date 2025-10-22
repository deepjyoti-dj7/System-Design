package com.airline.management.controller;

import com.airline.management.dto.airport.AirportRequest;
import com.airline.management.dto.airport.AirportResponse;
import com.airline.management.dto.util.ApiResponse;
import com.airline.management.service.AirportService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/airports")
@RequiredArgsConstructor
public class AirportController {
    private final AirportService airportService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<AirportResponse>> createAirport(@Valid @RequestBody AirportRequest request) {
        AirportResponse created = airportService.createAirport(request);
        return ResponseEntity.ok(ApiResponse.ok("Airport created", created));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<AirportResponse>> getAirport(@PathVariable Long id) {
        AirportResponse resp = airportService.getAirportById(id);
        return ResponseEntity.ok(ApiResponse.ok(resp));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<AirportResponse>>> getAllAirports() {
        List<AirportResponse> airports = airportService.getAllAirports();
        return ResponseEntity.ok(ApiResponse.ok(airports));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<AirportResponse>> updateAirport(@PathVariable Long id, @Valid @RequestBody AirportRequest request) {
        AirportResponse updated = airportService.updateAirport(id, request);
        return ResponseEntity.ok(ApiResponse.ok("Airport updated", updated));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> deleteAirport(@PathVariable Long id) {
        airportService.deleteAirport(id);
        return ResponseEntity.ok(ApiResponse.ok("Airport deleted", null));
    }
}
