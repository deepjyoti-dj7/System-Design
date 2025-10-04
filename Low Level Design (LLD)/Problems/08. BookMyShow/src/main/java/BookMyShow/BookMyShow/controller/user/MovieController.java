package BookMyShow.BookMyShow.controller.user;

import BookMyShow.BookMyShow.dto.MovieDto;
import BookMyShow.BookMyShow.service.MovieService;
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
    public ResponseEntity<ApiResponse<List<MovieDto.MovieResponse>>> getAllMovies() {
        logger.info("Fetching all movies");
        List<MovieDto.MovieResponse> movies = movieService.getAllMovies();
        return ResponseEntity.ok(new ApiResponse<>(true, "Fetched all movies successfully", movies));
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<ApiResponse<MovieDto.MovieResponse>> getMovie(@PathVariable Long id) {
        return movieService.getMovie(id)
                .map(movie -> ResponseEntity.ok(new ApiResponse<>(true, "Movie found", movie)))
                .orElse(ResponseEntity.status(404).body(new ApiResponse<>(false, "Movie not found", null)));
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<MovieDto.MovieResponse>>> searchMovies(@RequestParam String title) {
        List<MovieDto.MovieResponse> movies = movieService.searchMovies(title);
        return ResponseEntity.ok(new ApiResponse<>(true, "Search results", movies));
    }

    // ==================== STANDARD API RESPONSE ====================
    public record ApiResponse<T>(boolean success, String message, T data) {}
}
