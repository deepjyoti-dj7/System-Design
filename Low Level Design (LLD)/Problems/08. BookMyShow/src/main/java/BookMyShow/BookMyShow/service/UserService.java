package BookMyShow.BookMyShow.service;

import BookMyShow.BookMyShow.dto.UserDto;
import BookMyShow.BookMyShow.entity.User;
import BookMyShow.BookMyShow.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

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

    public UserDto.UserResponse createUser(UserDto.UserRequest request) {
        logger.info("Creating new user with email: {}", request.getEmail());
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());
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
                user.getEmail(),
                user.getPhone()
        );
    }
}
