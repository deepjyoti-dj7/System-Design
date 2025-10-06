package BookMyShow.BookMyShow.controller.admin;

import BookMyShow.BookMyShow.dto.ApiResponse;
import BookMyShow.BookMyShow.dto.SeatDto;
import BookMyShow.BookMyShow.service.SeatService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/admin/seats")
@RequiredArgsConstructor
public class AdminSeatController {

    private static final Logger logger = LoggerFactory.getLogger(AdminSeatController.class);
    private final SeatService seatService;

    // ==================== CREATE SEAT ====================
    @PostMapping
    public ResponseEntity<ApiResponse<SeatDto.SeatResponse>> addSeat(@Valid @RequestBody SeatDto.SeatRequest request) {
        logger.info("Creating new seat: {} for screen {}", request.getSeatNumber(), request.getScreenId());
        SeatDto.SeatResponse created = seatService.addSeat(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(true, "Seat created successfully", created));
    }

    // ==================== BULK CREATE SEATS ====================
    @PostMapping("/bulk")
    public ResponseEntity<ApiResponse<List<SeatDto.SeatResponse>>> addSeatsBulk(
            @Valid @RequestBody List<SeatDto.SeatRequest> requests) {
        logger.info("Bulk creating {} seats", requests.size());
        List<SeatDto.SeatResponse> createdSeats = seatService.addSeatsBulk(requests);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(true, "Bulk seats created successfully", createdSeats));
    }

    // ==================== UPDATE SEAT ====================
    @PatchMapping("/id/{id}")
    public ResponseEntity<ApiResponse<SeatDto.SeatResponse>> updateSeat(
            @PathVariable Long id, @RequestBody SeatDto.SeatRequest request) {
        return seatService.updateSeat(id, request)
                .map(updated -> ResponseEntity.ok(new ApiResponse<>(true, "Seat updated successfully", updated)))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>(false, "Seat not found", null)));
    }

    // ==================== DELETE SEAT ====================
    @DeleteMapping("/id/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteSeat(@PathVariable Long id) {
        boolean deleted = seatService.deleteSeat(id);
        if (deleted) {
            return ResponseEntity.ok(new ApiResponse<>(true, "Seat deleted successfully", null));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, "Seat not found", null));
        }
    }
}
