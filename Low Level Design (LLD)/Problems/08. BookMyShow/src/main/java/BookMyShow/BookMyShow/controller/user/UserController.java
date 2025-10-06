package BookMyShow.BookMyShow.controller.user;

import BookMyShow.BookMyShow.dto.UserDto;
import BookMyShow.BookMyShow.dto.ApiResponse;
import BookMyShow.BookMyShow.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    // ==================== SELF PROFILE ====================

    // Get own profile
    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserDto.UserResponse>> getMyProfile(Authentication auth) {
        logger.info("Fetching profile for username: {}", auth.getName());
        Long userId = userService.getUserIdByUsername(auth.getName());
        return userService.getUserById(userId)
                .map(user -> ResponseEntity.ok(
                        new ApiResponse<>(true, "Fetched profile successfully", user)
                ))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>(false, "User not found", null))
                );
    }

    // Update own profile
    @PatchMapping("/me")
    public ResponseEntity<ApiResponse<UserDto.UserResponse>> updateMyProfile(@RequestBody UserDto.UserRequest request,
                                                                             Authentication auth) {
        Long userId = userService.getUserIdByUsername(auth.getName());
        logger.info("Updating profile for userId: {}", userId);
        return userService.partialUpdateUser(userId, request)
                .map(updated -> ResponseEntity.ok(
                        new ApiResponse<>(true, "Profile updated successfully", updated)
                ))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>(false, "User not found", null))
                );
    }
}
