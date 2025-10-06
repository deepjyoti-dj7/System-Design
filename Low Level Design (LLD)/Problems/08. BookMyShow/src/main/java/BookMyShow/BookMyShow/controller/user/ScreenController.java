package BookMyShow.BookMyShow.controller.user;

import BookMyShow.BookMyShow.dto.ScreenDto;
import BookMyShow.BookMyShow.dto.ApiResponse;
import BookMyShow.BookMyShow.service.ScreenService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/screens")
@RequiredArgsConstructor
public class ScreenController {

    private static final Logger logger = LoggerFactory.getLogger(ScreenController.class);
    private final ScreenService screenService;

    // ==================== FETCH SCREENS ====================

    @GetMapping
    public ResponseEntity<ApiResponse<List<ScreenDto.ScreenResponse>>> getAllScreens() {
        logger.info("Fetching all screens");
        List<ScreenDto.ScreenResponse> screens = screenService.getAllScreens();
        return ResponseEntity.ok(new ApiResponse<>(true, "Fetched all screens successfully", screens));
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<ApiResponse<ScreenDto.ScreenResponse>> getScreen(@PathVariable Long id) {
        logger.info("Fetching a screen by id");
        return screenService.getScreen(id)
                .map(screen -> ResponseEntity.ok(new ApiResponse<>(true, "Screen found", screen)))
                .orElse(ResponseEntity.status(404).body(new ApiResponse<>(false, "Screen not found", null)));
    }

    @GetMapping("/theatre/{theatreId}")
    public ResponseEntity<ApiResponse<List<ScreenDto.ScreenResponse>>> getScreenByTheatre(@PathVariable Long theatreId) {
        logger.info("Fetching screen by theatre id");
        List<ScreenDto.ScreenResponse> screens = screenService.getScreensByTheatre(theatreId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Fetched all screens successfully for a theatre", screens));
    }
}
