package BookMyShow.BookMyShow.controller;

import BookMyShow.BookMyShow.dto.UserDto;
import BookMyShow.BookMyShow.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    // Only ADMINs can fetch all users
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<UserDto.UserResponse> getAllUsers() {
        logger.info("GET /api/users called");
        return userService.getAllUsers();
    }

    // Admins or the user themselves can fetch by ID
    @GetMapping("/id/{id}")
    @PreAuthorize("hasRole('ADMIN') or #id.toString() == authentication.principal.id")
    public ResponseEntity<UserDto.UserResponse> getUserById(@PathVariable Long id, Authentication authentication) {
        logger.info("GET /api/users/id/{} called", id);
        return userService.getUserById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Only ADMIN can search by email
    @GetMapping("/search")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserDto.UserResponse> getUserByEmail(@RequestParam String email) {
        logger.info("GET /api/users/search?email={} called", email);
        return userService.getUserByEmail(email)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Open for registration (anyone)
    @PostMapping
    public ResponseEntity<UserDto.UserResponse> createUser(@Valid @RequestBody UserDto.UserRequest request) {
        logger.info("POST /api/users called");
        return ResponseEntity.ok(userService.createUser(request));
    }

    // Admins or the user themselves can partially update
    @PatchMapping("/id/{id}")
    @PreAuthorize("hasRole('ADMIN') or #id.toString() == authentication.principal.id")
    public ResponseEntity<UserDto.UserResponse> partialUpdateUser(@PathVariable Long id,
                                                                  @RequestBody UserDto.UserRequest request,
                                                                  Authentication authentication) {
        logger.info("PATCH /api/users/id/{} called", id);
        return userService.partialUpdateUser(id, request)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Only ADMIN can delete users
    @DeleteMapping("/id/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        logger.info("DELETE /api/users/id/{} called", id);
        return userService.deleteUser(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}
