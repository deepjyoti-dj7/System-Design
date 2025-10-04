package BookMyShow.BookMyShow.controller.admin;

import BookMyShow.BookMyShow.dto.UserDto;
import BookMyShow.BookMyShow.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/users")
@RequiredArgsConstructor
public class AdminUserController {

    private static final Logger logger = LoggerFactory.getLogger(AdminUserController.class);
    private final UserService userService;

    // ==================== ADMIN USER MANAGEMENT ====================

    // Fetch all users
    @GetMapping
    public ResponseEntity<ApiResponse<List<UserDto.UserResponse>>> getAllUsers() {
        logger.info("Fetching all users (admin)");
        List<UserDto.UserResponse> users = userService.getAllUsers();
        return ResponseEntity.ok(
                new ApiResponse<>(true, "Fetched all users successfully", users)
        );
    }

    // Fetch user by ID
    @GetMapping("/id/{id}")
    public ResponseEntity<ApiResponse<UserDto.UserResponse>> getUserById(@PathVariable Long id) {
        logger.info("Fetching user by id {} (admin)", id);
        return userService.getUserById(id)
                .map(user -> ResponseEntity.ok(new ApiResponse<>(true, "User found", user)))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>(false, "User not found", null))
                );
    }

    // Search user by email
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<UserDto.UserResponse>> getUserByEmail(@RequestParam String email) {
        logger.info("Searching user by email {} (admin)", email);
        return userService.getUserByEmail(email)
                .map(user -> ResponseEntity.ok(new ApiResponse<>(true, "User found", user)))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>(false, "User not found", null))
                );
    }

    // Create a new user
    @PostMapping
    public ResponseEntity<ApiResponse<UserDto.UserResponse>> createUser(@Valid @RequestBody UserDto.UserRequest request) {
        logger.info("Creating new user with email {} (admin)", request.getEmail());
        UserDto.UserResponse created = userService.createUser(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(true, "User created successfully", created));
    }

    // Update user by ID
    @PatchMapping("/id/{id}")
    public ResponseEntity<ApiResponse<UserDto.UserResponse>> partialUpdateUser(@PathVariable Long id,
                                                                               @Valid @RequestBody UserDto.UserRequest request) {
        logger.info("Updating user id {} (admin)", id);
        return userService.partialUpdateUser(id, request)
                .map(updated -> ResponseEntity.ok(new ApiResponse<>(true, "User updated successfully", updated)))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>(false, "User not found", null))
                );
    }

    // Delete user
    @DeleteMapping("/id/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteUser(@PathVariable Long id) {
        logger.info("Deleting user id {} (admin)", id);
        boolean deleted = userService.deleteUser(id);
        if (deleted) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse<>(true, "User deleted successfully", null));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, "User not found", null));
        }
    }

    // ==================== STANDARD API RESPONSE CLASS ====================
    public record ApiResponse<T>(boolean success, String message, T data) { }
}
