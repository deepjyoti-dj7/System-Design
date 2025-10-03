package BookMyShow.BookMyShow.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class UserDto {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserRequest {
        @NotBlank(message = "Name is required")
        private String name;

        @Email(message = "Invalid email")
        private String email;

        private String phone;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UserResponse {
        private String id;
        private String name;
        private String email;
        private String phone;
    }
}
