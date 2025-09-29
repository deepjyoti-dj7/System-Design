package BookMyShow.BookMyShow.repository;

import BookMyShow.BookMyShow.entity.SeatLock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface SeatLockRepository extends JpaRepository<SeatLock, Long> {
    boolean existsBySeatIdAndShowIdAndExpiresAtAfter(Long seatId, Long showId, LocalDateTime now);
    void deleteBySessionId(String sessionId);
}
