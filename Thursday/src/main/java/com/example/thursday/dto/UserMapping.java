package com.example.thursday.dto;

import com.example.thursday.model.User;

import java.util.List;

public class UserMapping {

  public static List<UserDTO> toUserDTOList(List<User> users) {
    return users.stream()
            .map(user -> new UserDTO(
                            user.getUsername(),
                            user.getPassword(),
                            RoleMapping.toRoleDTOList(user.getRoles())
                    )
            )
            .toList();
  }

  public static User toUser(UserDTO userDTO) {
    User user = new User();
    updateUserFromDTO(user, userDTO);
    return user;
  }

  public static UserDTO toUserDTO(User user) {
    return new UserDTO(user.getUsername(), user.getPassword(), RoleMapping.toRoleDTOList(user.getRoles()));
  }

  public static void updateUserFromDTO(User user, UserDTO userDTO) {
    user.setUsername(user.getUsername());
    user.setPassword(user.getPassword());
    user.setRoles(user.getRoles());
  }


}
