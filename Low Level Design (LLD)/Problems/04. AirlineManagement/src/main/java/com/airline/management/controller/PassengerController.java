package com.airline.management.controller;

import com.airline.management.dto.passenger.PassengerRequest;
import com.airline.management.dto.passenger.PassengerResponse;
import com.airline.management.dto.util.ApiResponse;
import com.airline.management.service.PassengerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/passengers")
@RequiredArgsConstructor
public class PassengerController {

    private final PassengerService passengerService;

    @PostMapping("/booking/{bookingId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<PassengerResponse>> createPassenger(@PathVariable Long bookingId,
                                                                          @Valid @RequestBody PassengerRequest request) {
        PassengerResponse created = passengerService.createPassenger(bookingId, request);
        return ResponseEntity.ok(ApiResponse.ok("Passenger created", created));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PassengerResponse>> getPassenger(@PathVariable Long id) {
        PassengerResponse resp = passengerService.getPassengerById(id);
        return ResponseEntity.ok(ApiResponse.ok(resp));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<PassengerResponse>>> getAllPassengers() {
        List<PassengerResponse> passengers = passengerService.getAllPassengers();
        return ResponseEntity.ok(ApiResponse.ok(passengers));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<PassengerResponse>> updatePassenger(@PathVariable Long id,
                                                                          @Valid @RequestBody PassengerRequest request) {
        PassengerResponse updated = passengerService.updatePassenger(id, request);
        return ResponseEntity.ok(ApiResponse.ok("Passenger updated", updated));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> deletePassenger(@PathVariable Long id) {
        passengerService.deletePassenger(id);
        return ResponseEntity.ok(ApiResponse.ok("Passenger deleted", null));
    }
}
