package BookMyShow.BookMyShow.controller;

import BookMyShow.BookMyShow.entity.User;
import BookMyShow.BookMyShow.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/auth")
public class AdminController {

    private final UserService userService;

    public AdminController(UserService userService) {
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
    public ResponseEntity<String> login(@RequestParam String username,
                                        @RequestParam String password) {
        String token = userService.authenticate(username, password);
        return ResponseEntity.ok(token);
    }
}
