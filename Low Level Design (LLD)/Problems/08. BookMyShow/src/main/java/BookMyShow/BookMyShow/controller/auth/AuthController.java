package BookMyShow.BookMyShow.controller.auth;

import BookMyShow.BookMyShow.entity.User;
import BookMyShow.BookMyShow.service.UserService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestParam String username,
                                         @RequestParam String email,
                                         @RequestParam String password,
                                         @RequestParam String name,
                                         @RequestParam String phone) {

        User user = userService.register(username, email, password, name, phone, Set.of("ROLE_USER"));
        return ResponseEntity.ok(user);
    }

    @PostMapping(value = "/login", consumes = "application/x-www-form-urlencoded")
    public ResponseEntity<Map<String, String>> login(
            @RequestParam String username,
            @RequestParam String password
    ) {
        String token = userService.authenticate(username, password);

        return ResponseEntity.ok(Map.of(
                "access_token", token,
                "token_type", "bearer"
        ));
    }


    @Data
    public static class LoginRequest {
        private String username;
        private String password;
    }
}
