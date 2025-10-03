package BookMyShow.BookMyShow.service;

import BookMyShow.BookMyShow.dto.UserDto;
import BookMyShow.BookMyShow.entity.User;
import BookMyShow.BookMyShow.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Get all users
    public List<UserDto.UserResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    // Get user by ID
    public Optional<UserDto.UserResponse> getUserById(Long id) {
        return userRepository.findById(id).map(this::toResponse);
    }

    // Get user by email
    public Optional<UserDto.UserResponse> getUserByEmail(String email) {
        return userRepository.findByEmail(email).map(this::toResponse);
    }

    // Create user
    public UserDto.UserResponse createUser(UserDto.UserRequest request) {
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());
        User savedUser = userRepository.save(user);
        return toResponse(savedUser);
    }

    // Partial update
    public Optional<UserDto.UserResponse> partialUpdateUser(Long id, UserDto.UserRequest request) {
        return userRepository.findById(id).map(user -> {
            if (request.getName() != null) user.setName(request.getName());
            if (request.getEmail() != null) user.setEmail(request.getEmail());
            if (request.getPhone() != null) user.setPhone(request.getPhone());
            return toResponse(userRepository.save(user));
        });
    }

    // Delete user
    public boolean deleteUser(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Convert entity to DTO
    private UserDto.UserResponse toResponse(User user) {
        return new UserDto.UserResponse(
                String.valueOf(user.getId()),
                user.getName(),
                user.getEmail(),
                user.getPhone()
        );
    }
}
