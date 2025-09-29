package BookMyShow.BookMyShow.controller;

import BookMyShow.BookMyShow.entity.SeatLock;
import BookMyShow.BookMyShow.repository.SeatLockRepository;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/seatlocks")
public class SeatLockController {
    private final SeatLockRepository seatLockRepository;

    public SeatLockController(SeatLockRepository seatLockRepository) {
        this.seatLockRepository = seatLockRepository;
    }

    @GetMapping
    public List<SeatLock> getActiveLocks() {
        return seatLockRepository.findAll().stream()
                .filter(lock -> lock.getExpiresAt().isAfter(LocalDateTime.now()))
                .toList();
    }
}
