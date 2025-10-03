package BookMyShow.BookMyShow.controller;

import BookMyShow.BookMyShow.dto.UserDto;
import BookMyShow.BookMyShow.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Get all users
    @GetMapping
    public List<UserDto.UserResponse> getAllUsers() {
        return userService.getAllUsers();
    }

    // Get user by ID
    @GetMapping("/id/{id}")
    public ResponseEntity<UserDto.UserResponse> getUserById(@PathVariable Long id) {
        return userService.getUserById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Search user by email
    @GetMapping("/search")
    public ResponseEntity<UserDto.UserResponse> getUserByEmail(@RequestParam String email) {
        return userService.getUserByEmail(email)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Create user
    @PostMapping
    public ResponseEntity<UserDto.UserResponse> createUser(@Valid @RequestBody UserDto.UserRequest request) {
        return ResponseEntity.ok(userService.createUser(request));
    }

    // Partial update
    @PatchMapping("/id/{id}")
    public ResponseEntity<UserDto.UserResponse> partialUpdateUser(@PathVariable Long id,
                                                                  @RequestBody UserDto.UserRequest request) {
        return userService.partialUpdateUser(id, request)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Delete user
    @DeleteMapping("/id/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        return userService.deleteUser(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}
