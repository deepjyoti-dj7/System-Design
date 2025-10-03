package BookMyShow.BookMyShow.controller;

import BookMyShow.BookMyShow.dto.UserDto;
import BookMyShow.BookMyShow.service.UserService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<UserDto.UserResponse> getAllUsers() {
        logger.info("GET /api/users called");
        return userService.getAllUsers();
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<UserDto.UserResponse> getUserById(@PathVariable Long id) {
        logger.info("GET /api/users/id/{} called", id);
        return userService.getUserById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/search")
    public ResponseEntity<UserDto.UserResponse> getUserByEmail(@RequestParam String email) {
        logger.info("GET /api/users/search?email={} called", email);
        return userService.getUserByEmail(email)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<UserDto.UserResponse> createUser(@Valid @RequestBody UserDto.UserRequest request) {
        logger.info("POST /api/users called");
        return ResponseEntity.ok(userService.createUser(request));
    }

    @PatchMapping("/id/{id}")
    public ResponseEntity<UserDto.UserResponse> partialUpdateUser(@PathVariable Long id,
                                                                  @RequestBody UserDto.UserRequest request) {
        logger.info("PATCH /api/users/id/{} called", id);
        return userService.partialUpdateUser(id, request)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        logger.info("DELETE /api/users/id/{} called", id);
        return userService.deleteUser(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}
