package com.example.Running.Club.dto;

import com.example.Running.Club.model.Role;

import java.util.List;

public record UserResponseDTO(
        long id,
        String username,
        List<Role> roles
) {
}
