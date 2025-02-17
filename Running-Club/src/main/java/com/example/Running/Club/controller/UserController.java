package com.example.Running.Club.controller;

import com.example.Running.Club.dto.UserMapping;
import com.example.Running.Club.dto.UserRequestDTO;
import com.example.Running.Club.service.UserService;
import com.example.Running.Club.model.User;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

  private final UserService userService;
  private final PasswordEncoder passwordEncoder;

  @Autowired
  public UserController(UserService userService, PasswordEncoder passwordEncoder) {
    this.userService = userService;
    this.passwordEncoder = passwordEncoder;
  }

  @PostMapping("/auth/register")
  public ResponseEntity<?> addUser(@Valid @RequestBody UserRequestDTO userRequestDTO) {

    if (userService.existsUserByUsername(userRequestDTO.username())) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username taken, please choose another!");
    }

    User user = UserMapping.toUser(userRequestDTO);
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    user = userService.saveUser(user);

    return ResponseEntity.status(HttpStatus.CREATED)
            .body(UserMapping.toUserResponseDTO(user));
  }

}
