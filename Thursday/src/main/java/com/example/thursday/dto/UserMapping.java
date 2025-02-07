package com.example.thursday.dto;

import com.example.thursday.model.User;
import com.example.thursday.security.SecurityConfig;

import java.util.List;

public class UserMapping {

  public static List<UserRequestDTO> toUserDTOOutList(List<User> users) {
    return users.stream()
            .map(user -> new UserRequestDTO(
                            user.getUsername(),
                            user.getRoles()
                    )
            )
            .toList();
  }

  public static User toUser(UserResponseDTO userResponseDTO) {
    User user = new User();
    user.setUsername(userResponseDTO.username());
    user.setPassword(userResponseDTO.password());
    user.setRoles(userResponseDTO.roles());
    return user;
  }

  public static UserRequestDTO toUserOutDTO(User user) {
    return new UserRequestDTO(user.getUsername(), user.getRoles());
  }

}
