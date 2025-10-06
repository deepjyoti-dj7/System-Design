package BookMyShow.BookMyShow.controller.user;

import BookMyShow.BookMyShow.dto.TheatreDto;
import BookMyShow.BookMyShow.dto.ApiResponse;
import BookMyShow.BookMyShow.service.TheatreService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/theatres")
@RequiredArgsConstructor
public class TheatreController {

    private static final Logger logger = LoggerFactory.getLogger(TheatreController.class);
    private final TheatreService theatreService;

    // ==================== FETCH THEATRES ====================

    @GetMapping
    public ResponseEntity<ApiResponse<List<TheatreDto.TheatreResponse>>> getAllTheatres() {
        logger.info("Fetching all theatres");
        List<TheatreDto.TheatreResponse> theatres = theatreService.getAllTheatres();
        return ResponseEntity.ok(new ApiResponse<>(true, "Fetched all theatres successfully", theatres));
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<ApiResponse<TheatreDto.TheatreResponse>> getTheatre(@PathVariable Long id) {
        logger.info("Fetching a theatre by id");
        return theatreService.getTheatre(id)
                .map(theatre -> ResponseEntity.ok(new ApiResponse<>(true, "Theatre found", theatre)))
                .orElse(ResponseEntity.status(404).body(new ApiResponse<>(false, "Theatre not found", null)));
    }

    @GetMapping("/city/{city}")
    public ResponseEntity<ApiResponse<List<TheatreDto.TheatreResponse>>> getTheatresByCity(@PathVariable String city) {
        List<TheatreDto.TheatreResponse> theatres = theatreService.getTheatresByCity(city);
        return ResponseEntity.ok(new ApiResponse<>(true, "Theatres in city results", theatres));
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<TheatreDto.TheatreResponse>>> searchTheatres(@RequestParam String city) {
        List<TheatreDto.TheatreResponse> theatres = theatreService.getTheatresByCity(city);
        return ResponseEntity.ok(new ApiResponse<>(true, "Search results", theatres));
    }
}
