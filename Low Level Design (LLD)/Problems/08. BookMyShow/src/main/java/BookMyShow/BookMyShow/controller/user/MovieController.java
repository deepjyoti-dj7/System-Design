package BookMyShow.BookMyShow.controller.user;

import BookMyShow.BookMyShow.dto.MovieDto;
import BookMyShow.BookMyShow.dto.ApiResponse;
import BookMyShow.BookMyShow.service.MovieService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movies")
@RequiredArgsConstructor
public class MovieController {

    private static final Logger logger = LoggerFactory.getLogger(MovieController.class);
    private final MovieService movieService;

    // ==================== FETCH MOVIES ====================

    @GetMapping
    @Operation(summary = "Get all movies", description = "Returns all movies that exists")
    public ResponseEntity<ApiResponse<List<MovieDto.MovieResponse>>> getAllMovies() {
        logger.info("Fetching all movies");
        List<MovieDto.MovieResponse> movies = movieService.getAllMovies();
        return ResponseEntity.ok(new ApiResponse<>(true, "Fetched all movies successfully", movies));
    }

    @GetMapping("/id/{id}")
    @Operation(summary = "Get a movie by id", description = "Returns a movies by it's id")
    public ResponseEntity<ApiResponse<MovieDto.MovieResponse>> getMovie(@PathVariable Long id) {
        return movieService.getMovie(id)
                .map(movie -> ResponseEntity.ok(new ApiResponse<>(true, "Movie found", movie)))
                .orElse(ResponseEntity.status(404).body(new ApiResponse<>(false, "Movie not found", null)));
    }

    @GetMapping("/search")
    @Operation(summary = "Get a movies by name", description = "Returns a movie by searching it's name")
    public ResponseEntity<ApiResponse<List<MovieDto.MovieResponse>>> searchMovies(@RequestParam String title) {
        List<MovieDto.MovieResponse> movies = movieService.searchMovies(title);
        return ResponseEntity.ok(new ApiResponse<>(true, "Search results", movies));
    }
}
