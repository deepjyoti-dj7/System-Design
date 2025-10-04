package BookMyShow.BookMyShow.service;

import BookMyShow.BookMyShow.dto.MovieDto;
import BookMyShow.BookMyShow.entity.Movie;
import BookMyShow.BookMyShow.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class MovieService {

    private static final Logger logger = LoggerFactory.getLogger(MovieService.class);
    private final MovieRepository movieRepository;

    // Fetch all movies
    public List<MovieDto.MovieResponse> getAllMovies() {
        logger.info("Fetching all movies");
        return movieRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    // Get movie by ID
    public Optional<MovieDto.MovieResponse> getMovie(Long id) {
        logger.info("Fetching movie by id: {}", id);
        return movieRepository.findById(id).map(this::toResponse);
    }

    // Create new movie
    public MovieDto.MovieResponse addMovie(MovieDto.MovieRequest request) {
        Movie movie = toEntity(request);
        Movie saved = movieRepository.save(movie);
        logger.info("Movie created with id: {}", saved.getId());
        return toResponse(saved);
    }

    // Update existing movie
    public Optional<MovieDto.MovieResponse> updateMovie(Long id, MovieDto.MovieRequest request) {
        return movieRepository.findById(id).map(movie -> {
            movie.setTitle(request.getTitle());
            movie.setRuntime(request.getRuntime());
            movie.setLanguage(request.getLanguage());
            movie.setDescription(request.getDescription());
            movie.setGenre(request.getGenre());
            movie.setCertification(request.getCertification());
            movie.setReleaseDate(request.getReleaseDate());
            movie.setPosterUrl(request.getPosterUrl());
            movie.setTrailerUrl(request.getTrailerUrl());
            Movie updated = movieRepository.save(movie);
            logger.info("Movie updated with id: {}", updated.getId());
            return toResponse(updated);
        });
    }

    // Delete movie
    public boolean deleteMovie(Long id) {
        if (!movieRepository.existsById(id)) {
            logger.warn("Movie not found with id: {}", id);
            return false;
        }
        movieRepository.deleteById(id);
        logger.info("Movie deleted with id: {}", id);
        return true;
    }

    // Search by title
    public List<MovieDto.MovieResponse> searchMovies(String title) {
        logger.info("Searching movies with title containing: {}", title);
        return movieRepository.findByTitleContainingIgnoreCase(title)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    // Helper methods
    private MovieDto.MovieResponse toResponse(Movie movie) {
        return new MovieDto.MovieResponse(
                movie.getId(),
                movie.getTitle(),
                movie.getRuntime(),
                movie.getLanguage(),
                movie.getDescription(),
                movie.getGenre(),
                movie.getCertification(),
                movie.getReleaseDate(),
                movie.getPosterUrl(),
                movie.getTrailerUrl()
        );
    }

    private Movie toEntity(MovieDto.MovieRequest request) {
        return Movie.builder()
                .title(request.getTitle())
                .runtime(request.getRuntime())
                .language(request.getLanguage())
                .description(request.getDescription())
                .genre(request.getGenre())
                .certification(request.getCertification())
                .releaseDate(request.getReleaseDate())
                .posterUrl(request.getPosterUrl())
                .trailerUrl(request.getTrailerUrl())
                .build();
    }
}
