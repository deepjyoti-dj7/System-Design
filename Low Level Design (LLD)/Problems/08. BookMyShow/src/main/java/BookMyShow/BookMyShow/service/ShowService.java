package BookMyShow.BookMyShow.service;

import BookMyShow.BookMyShow.dto.ShowDto;
import BookMyShow.BookMyShow.entity.Movie;
import BookMyShow.BookMyShow.entity.Screen;
import BookMyShow.BookMyShow.entity.Show;
import BookMyShow.BookMyShow.repository.MovieRepository;
import BookMyShow.BookMyShow.repository.ScreenRepository;
import BookMyShow.BookMyShow.repository.ShowRepository;
import jakarta.validation.Valid;
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
public class ShowService {

    private static final Logger logger = LoggerFactory.getLogger(ShowService.class);

    private final ShowRepository showRepository;
    private final MovieRepository movieRepository;
    private final ScreenRepository screenRepository;

    // ==================== FETCH SHOWS ====================
    public List<ShowDto.ShowResponse> getAllShows() {
        logger.info("Fetching all shows");
        return showRepository.findAll().stream().map(this::toResponse).toList();
    }

    public Optional<ShowDto.ShowResponse> getShow(Long id) {
        logger.info("Fetching show by id: {}", id);
        return showRepository.findById(id).map(this::toResponse);
    }

    public List<ShowDto.ShowResponse> getShowsByScreen(Long screenId) {
        logger.info("Fetching shows for screenId: {}", screenId);
        return showRepository.findByScreenId(screenId).stream().map(this::toResponse).toList();
    }

    public List<ShowDto.ShowResponse> getShowsByMovie(Long movieId) {
        logger.info("Fetching shows for movieId: {}", movieId);
        return showRepository.findByMovieId(movieId).stream().map(this::toResponse).toList();
    }

    // ==================== CREATE SHOW ====================
    public ShowDto.ShowResponse addShow(@Valid ShowDto.ShowRequest request) {
        Show show = toEntity(request);
        Show saved = showRepository.save(show);
        logger.info("Show created with id: {}", saved.getId());
        return toResponse(saved);
    }

    // ==================== UPDATE SHOW ====================
    public Optional<ShowDto.ShowResponse> updateShow(Long id, @Valid ShowDto.ShowRequest request) {
        return showRepository.findById(id).map(show -> {
            if (request.getMovieId() != null && movieRepository.existsById(request.getMovieId()))
                show.setMovie(movieRepository.getReferenceById(request.getMovieId()));

            if (request.getScreenId() != null && screenRepository.existsById(request.getScreenId()))
                show.setScreen(screenRepository.getReferenceById(request.getScreenId()));

            if (request.getStartTime() != null) show.setStartTime(request.getStartTime());
            if (request.getEndTime() != null) show.setEndTime(request.getEndTime());
            if (request.getBasePrice() != null) show.setBasePrice(request.getBasePrice());

            Show updated = showRepository.save(show);
            logger.info("Show updated with id: {}", updated.getId());
            return toResponse(updated);
        });
    }

    // ==================== DELETE SHOW ====================
    public boolean deleteShow(Long id) {
        if (!showRepository.existsById(id)) {
            logger.warn("Show not found with id: {}", id);
            return false;
        }
        showRepository.deleteById(id);
        logger.info("Show deleted with id: {}", id);
        return true;
    }

    // ==================== PRIVATE METHODS ====================
    private ShowDto.ShowResponse toResponse(Show show) {
        return new ShowDto.ShowResponse(
                show.getId(),
                show.getMovie().getId(),
                show.getMovie().getTitle(),
                show.getScreen().getId(),
                show.getScreen().getName(),
                show.getStartTime(),
                show.getEndTime(),
                show.getBasePrice()
        );
    }

    private Show toEntity(ShowDto.ShowRequest request) {
        Movie movie = movieRepository.findById(request.getMovieId())
                .orElseThrow(() -> new IllegalArgumentException("Movie not found with id: " + request.getMovieId()));

        Screen screen = screenRepository.findById(request.getScreenId())
                .orElseThrow(() -> new IllegalArgumentException("Screen not found with id: " + request.getScreenId()));

        return Show.builder()
                .movie(movie)
                .screen(screen)
                .startTime(request.getStartTime())
                .endTime(request.getEndTime())
                .basePrice(request.getBasePrice())
                .build();
    }
}
