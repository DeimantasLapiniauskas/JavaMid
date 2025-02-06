package com.example.thursday.dto;

import com.example.thursday.model.Role;

import java.util.List;

public class RoleMapping {

  public static List<RoleDTO> toRoleDTOList(List<Role> roles) {
    return roles.stream().map(role -> new RoleDTO(
                    role.getName()
            )
    ).toList();
  }


}
