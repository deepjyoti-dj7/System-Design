package BookMyShow.BookMyShow.controller.user;

import BookMyShow.BookMyShow.entity.Theatre;
import BookMyShow.BookMyShow.service.TheatreService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/theatres")
public class TheatreController {
    private final TheatreService theatreService;

    public TheatreController(TheatreService theatreService) {
        this.theatreService = theatreService;
    }

    @GetMapping
    public List<Theatre> getAllTheatres() {
        return theatreService.getAllTheatres();
    }

    @GetMapping("/city/{city}")
    public List<Theatre> getTheatresByCity(@PathVariable String city) {
        return theatreService.getTheatresByCity(city);
    }

//    @PostMapping
//    public Theatre addTheatre(@RequestBody Theatre theatre) {
//        return theatreService.addTheatre(theatre);
//    }
}
