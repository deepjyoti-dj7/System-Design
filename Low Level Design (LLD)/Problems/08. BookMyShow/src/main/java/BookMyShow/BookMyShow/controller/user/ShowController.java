package BookMyShow.BookMyShow.controller.user;

import BookMyShow.BookMyShow.dto.ApiResponse;
import BookMyShow.BookMyShow.dto.ShowDto;
import BookMyShow.BookMyShow.service.ShowService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shows")
@RequiredArgsConstructor
public class ShowController {

    private static final Logger logger = LoggerFactory.getLogger(ShowController.class);
    private final ShowService showService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<ShowDto.ShowResponse>>> getAllShows() {
        List<ShowDto.ShowResponse> shows = showService.getAllShows();
        return ResponseEntity.ok(new ApiResponse<>(true, "Fetched all shows successfully", shows));
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<ApiResponse<ShowDto.ShowResponse>> getShow(@PathVariable Long id) {
        return showService.getShow(id)
                .map(show -> ResponseEntity.ok(new ApiResponse<>(true, "Show found", show)))
                .orElse(ResponseEntity.status(404).body(new ApiResponse<>(false, "Show not found", null)));
    }

    @GetMapping("/screen/{screenId}")
    public ResponseEntity<ApiResponse<List<ShowDto.ShowResponse>>> getShowsByScreen(@PathVariable Long screenId) {
        List<ShowDto.ShowResponse> shows = showService.getShowsByScreen(screenId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Fetched shows for screen successfully", shows));
    }

    @GetMapping("/movie/{movieId}")
    public ResponseEntity<ApiResponse<List<ShowDto.ShowResponse>>> getShowsByMovie(@PathVariable Long movieId) {
        List<ShowDto.ShowResponse> shows = showService.getShowsByMovie(movieId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Fetched shows for movie successfully", shows));
    }
}
