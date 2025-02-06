package com.example.thursday.dto;

import com.example.thursday.model.Role;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record UserDTO(
        @NotNull
        String username,

        @NotNull
        String password,

        @NotNull
        List<RoleDTO> roles
) {


}
//public record MovieDTO(
//        @Title
//        String title,
//
//        @NotNull
//        @Size(max = 50, message = "Field has to be between 2 and 50 characters long.")
//        @Pattern(
//                regexp = "^[A-Z][a-z]+$", // see: message below
//                message = "Name must start with a capital letter, and" +
//                        " be followed by at least one non-capital letter"
//        )
//        String director,
//
//        List<Screening> screenings,
//
//        @NotEmpty(message = "What, are you gonna make a movie without actors?")
//        List<Actor> actors) {
//
//}