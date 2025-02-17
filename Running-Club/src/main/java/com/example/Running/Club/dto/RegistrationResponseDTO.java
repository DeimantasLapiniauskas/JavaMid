package com.example.Running.Club.dto;

import java.time.LocalDateTime;

public record RegistrationResponseDTO(
        long id,
        long userId,
        String eventName,
        LocalDateTime registrationDate
) {
}
