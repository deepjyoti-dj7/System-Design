package BookMyShow.BookMyShow.service;

import BookMyShow.BookMyShow.entity.Show;
import BookMyShow.BookMyShow.repository.ShowRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ShowService {
    private final ShowRepository showRepository;

    public ShowService(ShowRepository showRepository) {
        this.showRepository = showRepository;
    }

    public List<Show> getAllShows() {
        return showRepository.findAll();
    }

    public Show addShow(Show show) {
        return showRepository.save(show);
    }

    public List<Show> getShowsForMovie(Long movieId, LocalDateTime from, LocalDateTime to) {
        return showRepository.findByMovieIdAndStartTimeBetween(movieId, from, to);
    }
}
