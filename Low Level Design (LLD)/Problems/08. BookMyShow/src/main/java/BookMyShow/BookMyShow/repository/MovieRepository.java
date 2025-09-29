package BookMyShow.BookMyShow.repository;

import BookMyShow.BookMyShow.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, Long> {
}
