package BookMyShow.BookMyShow.service;

import BookMyShow.BookMyShow.dto.UserDto;
import BookMyShow.BookMyShow.entity.User;
import BookMyShow.BookMyShow.repository.UserRepository;
import BookMyShow.BookMyShow.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    // ==================== AUTHENTICATION / REGISTRATION ====================

    public User register(String username, String email, String password, String name, String phone, Set<String> roles) {
        validateUserUniqueness(username, email);

        User user = User.builder()
                .username(username)
                .email(email)
                .password(passwordEncoder.encode(password))
                .name(name)
                .phone(phone)
                .roles(roles)
                .build();

        logger.info("Registering new user: {}", username);
        return userRepository.save(user);
    }

    public String authenticate(String username, String password) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Invalid username or password"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid username or password");
        }

        logger.info("User authenticated: {}", username);
        return jwtUtil.generateToken(user.getUsername(), user.getRoles());
    }

    // ==================== CRUD / DTO LAYER ====================

    public List<UserDto.UserResponse> getAllUsers() {
        logger.info("Fetching all users");
        return userRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    public Optional<UserDto.UserResponse> getUserById(Long id) {
        logger.info("Fetching user by id: {}", id);
        return userRepository.findById(id).map(this::toResponse);
    }

    public Long getUserIdByUsername(String username) {
        return userRepository.findByUsername(username)
                .map(User::getId)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public Optional<UserDto.UserResponse> getUserByEmail(String email) {
        logger.info("Searching user by email: {}", email);
        return userRepository.findByEmail(email).map(this::toResponse);
    }

    public UserDto.UserResponse createUser(UserDto.UserRequest request) {
        validateCreateRequest(request);

        User user = User.builder()
                .username(request.getUsername() != null ? request.getUsername() : request.getEmail())
                .name(request.getName())
                .email(request.getEmail())
                .phone(request.getPhone())
                .password(passwordEncoder.encode(request.getPassword()))
                .roles(Set.of("USER"))
                .build();

        User savedUser = userRepository.save(user);
        logger.info("User created with id: {}", savedUser.getId());
        return toResponse(savedUser);
    }

    public Optional<UserDto.UserResponse> partialUpdateUser(Long id, UserDto.UserRequest request) {
        return userRepository.findById(id).map(user -> {
            if (request.getName() != null) user.setName(request.getName());
            if (request.getEmail() != null) user.setEmail(request.getEmail());
            if (request.getPhone() != null) user.setPhone(request.getPhone());
            if (request.getUsername() != null) user.setUsername(request.getUsername());
            if (request.getPassword() != null && !request.getPassword().isBlank()) {
                user.setPassword(passwordEncoder.encode(request.getPassword()));
            }

            User updated = userRepository.save(user);
            logger.info("User updated with id: {}", updated.getId());
            return toResponse(updated);
        });
    }

    public boolean deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            logger.warn("User not found for deletion, id: {}", id);
            return false;
        }
        userRepository.deleteById(id);
        logger.info("User deleted with id: {}", id);
        return true;
    }

    // ==================== HELPER METHODS ====================

    private void validateUserUniqueness(String username, String email) {
        if (userRepository.findByUsername(username).isPresent()) {
            throw new RuntimeException("Username already exists");
        }
        if (userRepository.findByEmail(email).isPresent()) {
            throw new RuntimeException("Email already exists");
        }
    }

    private void validateCreateRequest(UserDto.UserRequest request) {
        if (request.getPassword() == null || request.getPassword().isBlank()) {
            throw new RuntimeException("Password cannot be null or empty");
        }
        if (request.getEmail() == null || request.getEmail().isBlank()) {
            throw new RuntimeException("Email cannot be null or empty");
        }
        validateUserUniqueness(
                request.getUsername() != null ? request.getUsername() : request.getEmail(),
                request.getEmail()
        );
    }

    private UserDto.UserResponse toResponse(User user) {
        return new UserDto.UserResponse(
                String.valueOf(user.getId()),
                user.getName(),
                user.getUsername(),
                user.getEmail(),
                user.getPhone()
        );
    }
}
