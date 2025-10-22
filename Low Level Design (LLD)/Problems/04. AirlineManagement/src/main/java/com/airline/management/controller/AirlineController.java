package com.airline.management.controller;

import com.airline.management.dto.airline.AirlineRequest;
import com.airline.management.dto.airline.AirlineResponse;
import com.airline.management.dto.util.ApiResponse;
import com.airline.management.service.AirlineService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/airlines")
@RequiredArgsConstructor
public class AirlineController {

    private final AirlineService airlineService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<AirlineResponse>> createAirline(@Valid @RequestBody AirlineRequest request) {
        AirlineResponse created = airlineService.createAirline(request);
        return ResponseEntity.ok(ApiResponse.ok("Airline created successfully", created));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<AirlineResponse>> getAirline(@PathVariable Long id) {
        AirlineResponse resp = airlineService.getAirlineById(id);
        return ResponseEntity.ok(ApiResponse.ok(resp));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<AirlineResponse>>> getAllAirlines() {
        List<AirlineResponse> airlines = airlineService.getAllAirlines();
        return ResponseEntity.ok(ApiResponse.ok(airlines));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<AirlineResponse>> updateAirline(@PathVariable Long id,
                                                                      @Valid @RequestBody AirlineRequest request) {
        AirlineResponse updated = airlineService.updateAirline(id, request);
        return ResponseEntity.ok(ApiResponse.ok("Airline updated successfully", updated));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> deleteAirline(@PathVariable Long id) {
        airlineService.deleteAirline(id);
        return ResponseEntity.ok(ApiResponse.ok("Airline deleted successfully", null));
    }
}
