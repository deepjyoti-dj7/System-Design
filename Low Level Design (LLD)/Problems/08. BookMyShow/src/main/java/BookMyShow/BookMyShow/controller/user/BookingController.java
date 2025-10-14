package BookMyShow.BookMyShow.controller.user;

import BookMyShow.BookMyShow.dto.BookingDto.BookingRequest;
import BookMyShow.BookMyShow.dto.BookingDto.BookingResponse;
import BookMyShow.BookMyShow.service.BookingService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @PostMapping
    @Operation(summary = "Create a new booking", description = "Creates a new booking for a movie with the given details")
    public ResponseEntity<BookingResponse> createBooking(@RequestBody BookingRequest request) {
        BookingResponse response = bookingService.createBooking(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/cancel/{bookingId}")
    @Operation(summary = "Cancel booking", description = "Cancel booking for a movie with the given details")
    public ResponseEntity<String> cancelBooking(@PathVariable Long bookingId) {
        bookingService.cancelBooking(bookingId);
        return ResponseEntity.ok("Booking cancelled successfully");
    }
}
