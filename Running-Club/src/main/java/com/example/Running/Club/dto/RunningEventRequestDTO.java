package com.example.Running.Club.dto;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record RunningEventRequestDTO(
        @NotNull
        @Size(min = 5, message = "Field has to be at least 5 characters long.")
        String name,

        @NotNull
        @Future(message = "Date must be in the future.")
        LocalDate calendarDate,

        @NotNull
        @Pattern(
                regexp = "^[a-zA-Z0-9_ ]+$",
                message = "location must consist of only letters, numbers and spaces."
        )
        String location,

        @Min(1) // the task asks it to just not be 0,
        // but in what situation would you want to put negative numbers here?
        int maxParticipants
) {
}
