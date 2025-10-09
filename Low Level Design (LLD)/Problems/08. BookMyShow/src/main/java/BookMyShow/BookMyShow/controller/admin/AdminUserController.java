package BookMyShow.BookMyShow.controller.admin;

import BookMyShow.BookMyShow.dto.UserDto;
import BookMyShow.BookMyShow.dto.ApiResponse;
import BookMyShow.BookMyShow.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import java.util.List;

@RestController
@RequestMapping("/api/admin/users")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth") // secured endpoints
public class AdminUserController {

    private static final Logger logger = LoggerFactory.getLogger(AdminUserController.class);
    private final UserService userService;

    // ==================== ADMIN USER MANAGEMENT ====================

    @GetMapping
    @Operation(summary = "Fetch all users", description = "Returns a list of all users (admin only)")
    public ResponseEntity<ApiResponse<List<UserDto.UserResponse>>> getAllUsers() {
        logger.info("Fetching all users (admin)");
        List<UserDto.UserResponse> users = userService.getAllUsers();
        return ResponseEntity.ok(
                new ApiResponse<>(true, "Fetched all users successfully", users)
        );
    }

    @GetMapping("/id/{id}")
    @Operation(summary = "Fetch user by ID", description = "Returns a single user by their ID (admin only)")
    public ResponseEntity<ApiResponse<UserDto.UserResponse>> getUserById(@PathVariable Long id) {
        logger.info("Fetching user by id {} (admin)", id);
        return userService.getUserById(id)
                .map(user -> ResponseEntity.ok(new ApiResponse<>(true, "User found", user)))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>(false, "User not found", null))
                );
    }

    @GetMapping("/search")
    @Operation(summary = "Search user by email", description = "Searches for a user by their email address (admin only)")
    public ResponseEntity<ApiResponse<UserDto.UserResponse>> getUserByEmail(@RequestParam String email) {
        logger.info("Searching user by email {} (admin)", email);
        return userService.getUserByEmail(email)
                .map(user -> ResponseEntity.ok(new ApiResponse<>(true, "User found", user)))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>(false, "User not found", null))
                );
    }

    @PostMapping
    @Operation(summary = "Create a new user", description = "Creates a new user with the given details (admin only)")
    public ResponseEntity<ApiResponse<UserDto.UserResponse>> createUser(@Valid @RequestBody UserDto.UserRequest request) {
        logger.info("Creating new user with email {} (admin)", request.getEmail());
        UserDto.UserResponse created = userService.createUser(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(true, "User created successfully", created));
    }

    @PatchMapping("/id/{id}")
    @Operation(summary = "Update user by ID", description = "Partially updates a user's details by ID (admin only)")
    public ResponseEntity<ApiResponse<UserDto.UserResponse>> partialUpdateUser(@PathVariable Long id,
                                                                               @Valid @RequestBody UserDto.UserRequest request) {
        logger.info("Updating user id {} (admin)", id);
        return userService.partialUpdateUser(id, request)
                .map(updated -> ResponseEntity.ok(new ApiResponse<>(true, "User updated successfully", updated)))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>(false, "User not found", null))
                );
    }

    @DeleteMapping("/id/{id}")
    @Operation(summary = "Delete user by ID", description = "Deletes a user by their ID (admin only)")
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
}
