package BookMyShow.BookMyShow.controller;

import BookMyShow.BookMyShow.entity.Seat;
import BookMyShow.BookMyShow.repository.SeatRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/api/seats")
public class SeatController {
    private final SeatRepository seatRepository;

    public SeatController(SeatRepository seatRepository) {
        this.seatRepository = seatRepository;
    }

    @GetMapping("/screen/{screenId}")
    public List<Seat> getSeatsForScreen(@PathVariable Long screenId) {
        return seatRepository.findByScreenId(screenId);
    }

    @PostMapping
    public Seat addSeat(@RequestBody Seat seat) {
        return seatRepository.save(seat);
    }
}