package BookMyShow.BookMyShow.controller.admin;

import BookMyShow.BookMyShow.dto.ApiResponse;
import BookMyShow.BookMyShow.dto.ShowDto;
import BookMyShow.BookMyShow.service.ShowService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/shows")
@RequiredArgsConstructor
public class AdminShowController {

    private static final Logger logger = LoggerFactory.getLogger(AdminShowController.class);
    private final ShowService showService;

    // ==================== CREATE SHOW ====================
    @PostMapping
    public ResponseEntity<ApiResponse<ShowDto.ShowResponse>> addShow(@Valid @RequestBody ShowDto.ShowRequest request) {
        ShowDto.ShowResponse created = showService.addShow(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(true, "Show created successfully", created));
    }

    // ==================== UPDATE SHOW ====================
    @PatchMapping("/id/{id}")
    public ResponseEntity<ApiResponse<ShowDto.ShowResponse>> updateShow(
            @PathVariable Long id, @RequestBody ShowDto.ShowRequest request) {
        return showService.updateShow(id, request)
                .map(updated -> ResponseEntity.ok(new ApiResponse<>(true, "Show updated successfully", updated)))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>(false, "Show not found", null)));
    }

    // ==================== DELETE SHOW ====================
    @DeleteMapping("/id/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteShow(@PathVariable Long id) {
        boolean deleted = showService.deleteShow(id);
        if (deleted) {
            return ResponseEntity.ok(new ApiResponse<>(true, "Show deleted successfully", null));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, "Show not found", null));
        }
    }
}
