package BookMyShow.BookMyShow.controller.admin;

import BookMyShow.BookMyShow.dto.ScreenDto;
import BookMyShow.BookMyShow.service.ScreenService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/admin/screens")
@RequiredArgsConstructor
public class AdminScreenController {

    private static final Logger logger = LoggerFactory.getLogger(AdminScreenController.class);
    private final ScreenService screenService;

    // ==================== CREATE SCREEN ====================
    @PostMapping
    public ResponseEntity<AdminScreenController.ApiResponse<ScreenDto.ScreenResponse>> addScreen(@Valid @RequestBody ScreenDto.ScreenRequest request) {
        logger.info("Creating new screen: {}", request.getName());
        ScreenDto.ScreenResponse created = screenService.addScreen(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(true, "Screen created successfully", created));
    }

    // ==================== UPDATE SCREEN ====================
    @PatchMapping("/id/{id}")
    public ResponseEntity<ApiResponse<ScreenDto.ScreenResponse>> updateScreen(
            @PathVariable Long id, @Valid @RequestBody ScreenDto.ScreenRequest request) {
        return screenService.updateScreen(id, request)
                .map(updated -> ResponseEntity.ok(new ApiResponse<>(true, "Screen updated successfully", updated)))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>(false, "Screen not found", null)));
    }

    // ==================== DELETE SCREEN ====================
    @DeleteMapping("id/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteScreen(@PathVariable Long id) {
        boolean deleted = screenService.deleteScreen(id);
        if (deleted) {
            return ResponseEntity.ok(new ApiResponse<>(true, "Screen deleted successfully", null));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, "Screen not found", null));
        }
    }

    // ==================== STANDARD API RESPONSE ====================
    public record ApiResponse<T>(boolean success, String message, T data) {}
}
