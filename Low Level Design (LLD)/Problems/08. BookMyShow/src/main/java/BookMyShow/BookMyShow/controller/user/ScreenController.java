package BookMyShow.BookMyShow.controller.user;

import BookMyShow.BookMyShow.entity.Screen;
import BookMyShow.BookMyShow.repository.ScreenRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/screens")
public class ScreenController {
    private final ScreenRepository screenRepository;

    public ScreenController(ScreenRepository screenRepository) {
        this.screenRepository = screenRepository;
    }

    @GetMapping
    public List<Screen> getAllScreens() {
        return screenRepository.findAll();
    }

    @PostMapping
    public Screen addScreen(@RequestBody Screen screen) {
        return screenRepository.save(screen);
    }
}
