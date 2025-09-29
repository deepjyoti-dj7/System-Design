package BookMyShow.BookMyShow.repository;

import BookMyShow.BookMyShow.entity.Show;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShowRepository extends JpaRepository<Show, Long> {
    List<Show> findByMovieIdAndStartTimeBetween(Long movieId, LocalDateTime from, LocalDateTime to);
    List<Show> findByScreenId(Long screenId);
}
