package BookMyShow.BookMyShow.service;

import BookMyShow.BookMyShow.entity.SeatLock;
import BookMyShow.BookMyShow.repository.SeatLockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SeatLockService {

    private final SeatLockRepository seatLockRepository;

    public void lockSeats(List<Long> seatIds, Long showId, String sessionId) {
        LocalDateTime now = LocalDateTime.now();

        for (Long seatId : seatIds) {
            boolean isLocked = seatLockRepository.existsBySeatIdAndShowIdAndExpiresAtAfter(
                    seatId, showId, now
            );

            if (isLocked) {
                throw new RuntimeException("Seat " + seatId + " is already locked or booked.");
            }

            SeatLock seatLock = SeatLock.builder()
                    .seatId(seatId)
                    .showId(showId)
                    .sessionId(sessionId)
                    .lockedAt(now)
                    .expiresAt(now.plusMinutes(10)) // lock valid for 10 mins
                    .build();

            seatLockRepository.save(seatLock);
        }
    }

    public void unlockSeats(Long showId, List<Long> seatIds, String sessionId) {
        seatLockRepository.deleteByShowIdAndSeatIdInAndSessionId(showId, seatIds, sessionId);
    }

    public void removeExpiredLocks() {
        seatLockRepository.deleteByExpiresAtBefore(LocalDateTime.now());
    }

    public boolean isSeatLocked(Long seatId, Long showId) {
        return seatLockRepository.existsBySeatIdAndShowIdAndExpiresAtAfter(
                seatId, showId, LocalDateTime.now()
        );
    }
}
