package com.example.Running.Club.dto;

import com.example.Running.Club.model.User;
import jakarta.validation.constraints.NotNull;

public record RegistrationRequestDTO(
        @NotNull
        User user
) {
}
