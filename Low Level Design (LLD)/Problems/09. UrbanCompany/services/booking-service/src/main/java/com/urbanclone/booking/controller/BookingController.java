package com.urbanclone.booking.controller;

import com.urbanclone.booking.dto.BookingCreateRequest;
import com.urbanclone.booking.dto.BookingDto;
import com.urbanclone.booking.entity.BookingStatus;
import com.urbanclone.booking.service.BookingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/bookings")
@RequiredArgsConstructor
public class BookingController {
    
    private final BookingService bookingService;
    
    @PostMapping
    public ResponseEntity<BookingDto> createBooking(
            @RequestHeader("X-User-Id") Long customerId,
            @Valid @RequestBody BookingCreateRequest request) {
        BookingDto booking = bookingService.createBooking(customerId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(booking);
    }
    
    @GetMapping("/{bookingId}")
    public ResponseEntity<BookingDto> getBooking(@PathVariable Long bookingId) {
        BookingDto booking = bookingService.getBooking(bookingId);
        return ResponseEntity.ok(booking);
    }
    
    @GetMapping("/number/{bookingNumber}")
    public ResponseEntity<BookingDto> getBookingByNumber(@PathVariable String bookingNumber) {
        BookingDto booking = bookingService.getBookingByNumber(bookingNumber);
        return ResponseEntity.ok(booking);
    }
    
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<Page<BookingDto>> getCustomerBookings(
            @PathVariable Long customerId,
            Pageable pageable) {
        Page<BookingDto> bookings = bookingService.getCustomerBookings(customerId, pageable);
        return ResponseEntity.ok(bookings);
    }
    
    @GetMapping("/partner/{partnerId}")
    public ResponseEntity<Page<BookingDto>> getPartnerBookings(
            @PathVariable Long partnerId,
            Pageable pageable) {
        Page<BookingDto> bookings = bookingService.getPartnerBookings(partnerId, pageable);
        return ResponseEntity.ok(bookings);
    }
    
    @PutMapping("/{bookingId}/assign-partner/{partnerId}")
    public ResponseEntity<BookingDto> assignPartner(
            @PathVariable Long bookingId,
            @PathVariable Long partnerId) {
        BookingDto booking = bookingService.assignPartner(bookingId, partnerId);
        return ResponseEntity.ok(booking);
    }
    
    @PutMapping("/{bookingId}/status")
    public ResponseEntity<BookingDto> updateStatus(
            @PathVariable Long bookingId,
            @RequestParam BookingStatus status) {
        BookingDto booking = bookingService.updateBookingStatus(bookingId, status);
        return ResponseEntity.ok(booking);
    }
    
    @PutMapping("/{bookingId}/cancel")
    public ResponseEntity<BookingDto> cancelBooking(
            @PathVariable Long bookingId,
            @RequestParam(required = false) String reason) {
        BookingDto booking = bookingService.cancelBooking(bookingId, reason);
        return ResponseEntity.ok(booking);
    }
}
