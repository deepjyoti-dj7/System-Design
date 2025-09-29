package BookMyShow.BookMyShow.repository;

import BookMyShow.BookMyShow.entity.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SeatRepository extends JpaRepository<Seat, Long> {
    List<Seat> findByScreenIdAndSeatNumberIn(Long screenId, List<String> seatNumbers);
    List<Seat> findByScreenId(Long screenId);
}
