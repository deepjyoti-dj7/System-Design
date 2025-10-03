package BookMyShow.BookMyShow.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

public class UserDto {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserRequest {
        @NotBlank(message = "Name is required")
        private String name;

        @NotBlank(message = "Username is required")
        private String username;

        @Email(message = "Invalid email")
        @NotBlank(message = "Email is required")
        private String email;

        @NotBlank(message = "Password is required")
        private String password;

        private String phone;

        private Set<String> roles; // Optional: can be used for admin creation
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UserResponse {
        private String id;
        private String name;
        private String username;
        private String email;
        private String phone;
        // Do NOT return password in responses for security reasons
    }
}
