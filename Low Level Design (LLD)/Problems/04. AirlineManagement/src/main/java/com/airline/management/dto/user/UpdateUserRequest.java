package com.airline.management.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateUserRequest {

    @Size(min = 3, max = 50)
    private String username; // if we allow username changes

    @Size(min = 6, max = 100)
    private String password; // changing password

    @Email
    private String email; // changing email

    private String firstName;
    private String lastName;
}

