package com.example.missedtuesday.dto;

import com.example.missedtuesday.model.Role;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record UserResponseDTO(
        @NotNull
        String username,

        @NotNull
        String password,

        @NotNull
        List<Role> roles
) {
}