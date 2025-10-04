package BookMyShow.BookMyShow.controller.user;

import BookMyShow.BookMyShow.dto.MovieDto;
import BookMyShow.BookMyShow.service.MovieService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    public ResponseEntity<List<MovieDto.MovieResponse>> getAllMovies() {
        logger.info("Fetching all movies");
        return ResponseEntity.ok(movieService.getAllMovies());
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<MovieDto.MovieResponse> getMovie(@PathVariable Long id) {
        return movieService.getMovie(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping("/search")
    public ResponseEntity<List<MovieDto.MovieResponse>> searchMovies(@RequestParam String title) {
        return ResponseEntity.ok(movieService.searchMovies(title));
    }

    // ==================== CREATE / UPDATE ====================

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MovieDto.MovieResponse> addMovie(@Valid @RequestBody MovieDto.MovieRequest request) {
        logger.info("Creating new movie: {}", request.getTitle());
        return ResponseEntity.status(HttpStatus.CREATED).body(movieService.addMovie(request));
    }

    @PatchMapping("/id/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MovieDto.MovieResponse> updateMovie(
            @PathVariable Long id,
            @RequestBody MovieDto.MovieRequest request) {
        return movieService.updateMovie(id, request)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // ==================== DELETE ====================

    @DeleteMapping("/id/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteMovie(@PathVariable Long id) {
        boolean deleted = movieService.deleteMovie(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
