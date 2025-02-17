package com.example.Running.Club.dto;

import com.example.Running.Club.model.Role;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.List;

public record UserRequestDTO(
        @NotNull
        @Size(min = 4, max = 100,
                message = "Field has to be between 4 and 100 characters long.")
        @Pattern(
                regexp = "^[a-z0-9]+$",
                message = "Name must consist of only lowercase letters and/or numbers"
        )
        String username,

        @NotNull
        @Size(min = 6,
                message = "Password has to be at least 6 characters long.")
        String password,

        @NotNull
        List<Role> roles
) {
}
