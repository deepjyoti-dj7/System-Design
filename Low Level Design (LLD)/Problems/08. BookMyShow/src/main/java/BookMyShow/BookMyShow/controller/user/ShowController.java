package BookMyShow.BookMyShow.controller.user;

import BookMyShow.BookMyShow.entity.Show;
import BookMyShow.BookMyShow.service.ShowService;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/shows")
public class ShowController {
    private final ShowService showService;

    public ShowController(ShowService showService) {
        this.showService = showService;
    }

    @GetMapping
    public List<Show> getAllShows() {
        return showService.getAllShows();
    }

    @PostMapping
    public Show addShow(@RequestBody Show show) {
        return showService.addShow(show);
    }

    @GetMapping("/movie/{movieId}")
    public List<Show> getShowsForMovie(@PathVariable Long movieId,
                                       @RequestParam String from,
                                       @RequestParam String to) {
        LocalDateTime fromTime = LocalDateTime.parse(from);
        LocalDateTime toTime = LocalDateTime.parse(to);
        return showService.getShowsForMovie(movieId, fromTime, toTime);
    }
}
