package com.example.Running.Club.dto;

import com.example.Running.Club.model.Role;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.List;

public record UserResponseDTO(
        long id,
        String username,
        List<Role> roles
) {
}
