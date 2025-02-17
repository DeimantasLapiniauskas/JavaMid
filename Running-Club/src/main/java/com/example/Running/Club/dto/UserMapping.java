package com.example.Running.Club.dto;

import com.example.Running.Club.model.User;

import java.util.List;

public class UserMapping {

  public static List<UserRequestDTO> toUserDTOList(List<User> users) {
    return users.stream()
            .map(user -> new UserRequestDTO(
                            user.getUsername(),
                            user.getPassword(),
                            user.getRoles()
                    )
            )
            .toList();
  }

  public static User toUser(UserRequestDTO userRequestDTO) {
    User user = new User();
    user.setUsername(userRequestDTO.username());
    user.setPassword(userRequestDTO.password());
    user.setRoles(userRequestDTO.roles());
    return user;
  }

  public static UserResponseDTO toUserResponseDTO(User user) {
    return new UserResponseDTO(user.getId(), user.getUsername(), user.getRoles());
  }
}
