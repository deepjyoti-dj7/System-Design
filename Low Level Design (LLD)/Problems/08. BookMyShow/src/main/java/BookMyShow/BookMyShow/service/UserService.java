package BookMyShow.BookMyShow.service;

import BookMyShow.BookMyShow.dto.UserDto;
import BookMyShow.BookMyShow.entity.User;
import BookMyShow.BookMyShow.repository.UserRepository;
import BookMyShow.BookMyShow.security.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    // ==================== AUTHENTICATION / REGISTRATION ====================

    public User register(String username, String email, String password, String name, String phone, Set<String> roles) {
        if (userRepository.findByUsername(username).isPresent()) {
            throw new RuntimeException("Username already exists");
        }
        if (userRepository.findByEmail(email).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        User user = User.builder()
                .username(username)
                .email(email)
                .password(passwordEncoder.encode(password))
                .name(name)
                .phone(phone)
                .roles(roles)
                .build();

        return userRepository.save(user);
    }

    public String authenticate(String username, String password) {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isEmpty()) throw new RuntimeException("Invalid username or password");

        User user = optionalUser.get();
        if (!passwordEncoder.matches(password, user.getPassword()))
            throw new RuntimeException("Invalid username or password");

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

    public Optional<UserDto.UserResponse> getUserByEmail(String email) {
        logger.info("Searching user by email: {}", email);
        return userRepository.findByEmail(email).map(this::toResponse);
    }

    // Updated createUser method: sets password, username, and default ROLE_USER
    public UserDto.UserResponse createUser(UserDto.UserRequest request) {
        logger.info("Creating new user with email: {}", request.getEmail());

        if (request.getPassword() == null || request.getPassword().isEmpty()) {
            throw new RuntimeException("Password cannot be null or empty");
        }

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());
        user.setUsername(request.getUsername() != null ? request.getUsername() : request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRoles(Set.of("ROLE_USER")); // default role

        User savedUser = userRepository.save(user);
        logger.info("User created with id: {}", savedUser.getId());
        return toResponse(savedUser);
    }

    public Optional<UserDto.UserResponse> partialUpdateUser(Long id, UserDto.UserRequest request) {
        logger.info("Updating user id: {}", id);
        return userRepository.findById(id).map(user -> {
            if (request.getName() != null) user.setName(request.getName());
            if (request.getEmail() != null) user.setEmail(request.getEmail());
            if (request.getPhone() != null) user.setPhone(request.getPhone());
            if (request.getPassword() != null) user.setPassword(passwordEncoder.encode(request.getPassword()));
            if (request.getUsername() != null) user.setUsername(request.getUsername());
            User updated = userRepository.save(user);
            logger.info("User updated with id: {}", updated.getId());
            return toResponse(updated);
        });
    }

    public boolean deleteUser(Long id) {
        logger.info("Deleting user with id: {}", id);
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            logger.info("User deleted with id: {}", id);
            return true;
        }
        logger.warn("User not found for deletion, id: {}", id);
        return false;
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
