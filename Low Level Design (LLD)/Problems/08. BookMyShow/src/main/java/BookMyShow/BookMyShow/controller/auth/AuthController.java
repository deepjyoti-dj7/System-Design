package BookMyShow.BookMyShow.controller.auth;

import BookMyShow.BookMyShow.entity.User;
import BookMyShow.BookMyShow.service.UserService;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestParam String username,
                                         @RequestParam String email,
                                         @RequestParam String password,
                                         @RequestParam String name,
                                         @RequestParam String phone) {

        User user = userService.register(username, email, password, name, phone, Set.of("ROLE_USER"));
        return ResponseEntity.ok(user);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest request) {
        String token = userService. authenticate(request.getUsername(), request.getPassword());
        return ResponseEntity.ok(token);
    }

    @Data
    public static class LoginRequest {
        private String username;
        private String password;
    }

}
