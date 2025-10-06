package BookMyShow.BookMyShow.controller.user;

import BookMyShow.BookMyShow.dto.ApiResponse;
import BookMyShow.BookMyShow.dto.SeatDto;
import BookMyShow.BookMyShow.service.SeatService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("api/seats")
@RequiredArgsConstructor
public class SeatController {

    private static final Logger logger = LoggerFactory.getLogger(SeatController.class);
    private final SeatService seatService;

    // ==================== FETCH SEATS FOR A SCREEN ====================
    @GetMapping("/screen/{screenId}")
    public ResponseEntity<ApiResponse<List<SeatDto.SeatResponse>>> getSeatsByScreen(@PathVariable Long screenId) {
        logger.info("Fetching seats for screen id {}", screenId);
        List<SeatDto.SeatResponse> seats = seatService.getSeatsByScreen(screenId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Fetched all seats for screen", seats));
    }
}
