package BookMyShow.BookMyShow.controller.admin;

import BookMyShow.BookMyShow.dto.MovieDto;
import BookMyShow.BookMyShow.dto.ApiResponse;
import BookMyShow.BookMyShow.service.MovieService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/movies")
@RequiredArgsConstructor
public class AdminMovieController {

    private static final Logger logger = LoggerFactory.getLogger(AdminMovieController.class);
    private final MovieService movieService;

    // ==================== CREATE MOVIE ====================
    @PostMapping
    @Operation(summary = "Create a new movie", description = "Creates a new movie with the given details (admin only)")
    public ResponseEntity<ApiResponse<MovieDto.MovieResponse>> addMovie(@Valid @RequestBody MovieDto.MovieRequest request) {
        logger.info("Creating new movie: {}", request.getTitle());
        MovieDto.MovieResponse created = movieService.addMovie(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(true, "Movie created successfully", created));
    }

    // ==================== BULK CREATE MOVIES ====================
    @PostMapping("/bulk")
    @Operation(summary = "Create movies in bulk", description = "Creates movies with the given details (admin only)")
    public ResponseEntity<ApiResponse<List<MovieDto.MovieResponse>>> addMovies(
            @Valid @RequestBody List<MovieDto.MovieRequest> requests) {

        logger.info("Creating {} new movies (bulk)", requests.size());
        List<MovieDto.MovieResponse> createdMovies = requests.stream()
                .map(movieService::addMovie)
                .toList();

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(true, "Movies created successfully", createdMovies));
    }


    // ==================== UPDATE MOVIE ====================
    @PatchMapping("/id/{id}")
    @Operation(summary = "Update movie by ID", description = "Partially updates a movie's details by ID (admin only)")
    public ResponseEntity<ApiResponse<MovieDto.MovieResponse>> updateMovie(
            @PathVariable Long id,
            @Valid @RequestBody MovieDto.MovieRequest request) {
        return movieService.updateMovie(id, request)
                .map(updated -> ResponseEntity.ok(new ApiResponse<>(true, "Movie updated successfully", updated)))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>(false, "Movie not found", null)));
    }

    // ==================== DELETE MOVIE ====================
    @DeleteMapping("/id/{id}")
    @Operation(summary = "Delete movie by ID", description = "Deletes a movie by their ID (admin only)")
    public ResponseEntity<ApiResponse<Void>> deleteMovie(@PathVariable Long id) {
        boolean deleted = movieService.deleteMovie(id);
        if (deleted) {
            return ResponseEntity.ok(new ApiResponse<>(true, "Movie deleted successfully", null));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, "Movie not found", null));
        }
    }
}
