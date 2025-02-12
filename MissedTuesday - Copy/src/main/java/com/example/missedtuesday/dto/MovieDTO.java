package com.example.missedtuesday.dto;

import com.example.missedtuesday.model.Actor;
import com.example.missedtuesday.model.Screening;
import com.example.missedtuesday.validation.Title;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.List;

public record MovieDTO(
        @Title
        String title,

        @NotNull
        @Size(max = 50, message = "Field has to be between 2 and 50 characters long.")
        @Pattern(
                regexp = "^[A-Z][a-z]+$", // see: message below
                message = "Name must start with a capital letter, and" +
                        " be followed by at least one non-capital letter"
        )
        String director,

        List<Screening> screenings,

//        @NotEmpty(message = "What, are you gonna make a movie without actors?")
        List<Actor> actors) {

}
