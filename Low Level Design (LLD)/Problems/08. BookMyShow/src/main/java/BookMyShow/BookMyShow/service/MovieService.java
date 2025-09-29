package BookMyShow.BookMyShow.service;

import BookMyShow.BookMyShow.entity.Movie;
import BookMyShow.BookMyShow.repository.MovieRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieService {
    private final MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    public Movie getMovie(Long id) {
        return movieRepository.findById(id).orElseThrow(() -> new RuntimeException("Movie not found"));
    }

    public Movie addMovie(Movie movie) {
        return movieRepository.save(movie);
    }

    public Movie updateMovie(Long id, Movie details) {
        Movie movie = getMovie(id);
        movie.setTitle(details.getTitle());
        movie.setRuntime(details.getRuntime());
        movie.setLanguage(details.getLanguage());
        movie.setDescription(details.getDescription());
        return movieRepository.save(movie);
    }

    public void deleteMovie(Long id) {
        movieRepository.deleteById(id);
    }
}
