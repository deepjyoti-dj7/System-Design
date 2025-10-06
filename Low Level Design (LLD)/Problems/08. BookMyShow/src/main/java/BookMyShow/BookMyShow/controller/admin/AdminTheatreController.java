package BookMyShow.BookMyShow.controller.admin;

import BookMyShow.BookMyShow.dto.TheatreDto;
import BookMyShow.BookMyShow.dto.ApiResponse;
import BookMyShow.BookMyShow.service.TheatreService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/admin/theatres")
@RequiredArgsConstructor
public class AdminTheatreController {

    private static final Logger logger = LoggerFactory.getLogger(AdminTheatreController.class);
    private final TheatreService theatreService;

    // ==================== CREATE THEATRE ====================
    @PostMapping
    public ResponseEntity<ApiResponse<TheatreDto.TheatreResponse>> addTheatre(@Valid @RequestBody TheatreDto.TheatreRequest request) {
        logger.info("Creating new theatre: {}", request.getName());
        TheatreDto.TheatreResponse created = theatreService.addTheatre(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(true, "Theatre created successfully", created));
    }

    // ==================== BULK CREATE THEATRES ====================
    @PostMapping("/bulk")
    public  ResponseEntity<ApiResponse<List<TheatreDto.TheatreResponse>>> addTheatres(
            @Valid @RequestBody List<TheatreDto.TheatreRequest> requests) {
        logger.info("Creating {} new threatres (bulk)", requests.size());
        List<TheatreDto.TheatreResponse> createdTheatres = requests.stream()
                .map(theatreService::addTheatre)
                .toList();

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(true, "Theatres created successfully", createdTheatres));
    }

    // ==================== UPDATE THEATRE ====================
    @PatchMapping("/id/{id}")
    public ResponseEntity<ApiResponse<TheatreDto.TheatreResponse>> updateTheatre(
            @PathVariable Long id, @Valid @RequestBody TheatreDto.TheatreRequest request) {
        return theatreService.updateTheatre(id, request)
                .map(updated -> ResponseEntity.ok(new ApiResponse<>(true, "Theatre updated successfully", updated)))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>(false, "Theatre not found", null)));
    }

    // ==================== DELETE THEATRE ====================
    @DeleteMapping("/id/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteMovie(@PathVariable Long id) {
        boolean deleted = theatreService.deleteTheatre(id);
        if (deleted) {
            return ResponseEntity.ok(new ApiResponse<>(true, "Theatre deleted successfully", null));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, "Theatre not found", null));
        }
    }
}
