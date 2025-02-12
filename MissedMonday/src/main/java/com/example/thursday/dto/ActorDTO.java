package com.example.thursday.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record ActorDTO(
        @NotNull
        @Size(min = 2, max = 50, message = "Field has to be between 2 and 50 characters long.")
        String your,

        @NotNull
        @Pattern(
                regexp = "^[A-Z][a-z]+$",
                message = "Liking must start with a capital letter, and" +
                        " be followed only by (at least one) non-capital letters"
        )
        String liking) {
}
