package com.example.thursday.dto;

import com.example.thursday.model.Role;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record UserRequestDTO( // like UserResponseDTO, but without password

                              @NotNull
                              String username,

                              @NotNull
                              List<Role> roles
) {


}