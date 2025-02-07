package com.example.thursday.controller;

import com.example.thursday.dto.UserMapping;
import com.example.thursday.dto.UserRequestDTO;
import com.example.thursday.dto.UserResponseDTO;
import com.example.thursday.model.User;
import com.example.thursday.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class UserController {
  private final UserService userService;
  private final PasswordEncoder passwordEncoder;

  @Autowired
  public UserController(UserService userService, PasswordEncoder passwordEncoder) {
    this.userService = userService;
    this.passwordEncoder = passwordEncoder;
  }

  @GetMapping("/users")
  public ResponseEntity<List<UserRequestDTO>> getAllUsers() {
    return ResponseEntity.ok(UserMapping.toUserDTOOutList(userService.findAllUsers()));
  }

  @GetMapping("/users/{id}")
  public ResponseEntity<UserRequestDTO> getUserById(@PathVariable long id) {

    Optional<User> foundUser = userService.findUserById(id);
    if (foundUser.isEmpty()) {
      return ResponseEntity.notFound().build();
    }

    return ResponseEntity.ok(UserMapping.toUserOutDTO(foundUser.get()));
  }

  @PostMapping("/users")
  public ResponseEntity<?> addUser(@Valid @RequestBody UserResponseDTO userResponseDTO) {
    if (userService.existsUserByUsername(userResponseDTO.username())) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username taken, please choose another!");
    }

    User user = UserMapping.toUser(userResponseDTO);
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    userService.saveUser(user);

    return ResponseEntity.status(HttpStatus.CREATED)
            .body(UserMapping.toUserOutDTO(UserMapping.toUser(userResponseDTO)));
  }

  @PutMapping("/users/{id}")
  public ResponseEntity<?> updateUser(@PathVariable long id, @Valid @RequestBody UserResponseDTO userResponseDTO) {
    if (userService.existsUserByUsernameAndNotId(userResponseDTO.username(), id)) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username already exists!");
    }

    if (!userService.existsUserById(id)) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User not found!");
    }

    User userFromDB = userService.findUserById(id).get();
    userFromDB.setUsername(userResponseDTO.username());
    userFromDB.setPassword(passwordEncoder.encode(userResponseDTO.password()));
    userFromDB.setRoles(userResponseDTO.roles());

    userService.saveUser(userFromDB);

    return ResponseEntity.ok(
            UserMapping.toUserOutDTO(
                    userService.saveUser(userFromDB)
            )
    );
  }

  @DeleteMapping("/users/{id}")
  public ResponseEntity<?> deleteUser(@PathVariable long id) {
    if (!userService.existsUserById(id)) {
      return ResponseEntity.notFound().build();
    }
    userService.deleteUserById(id);

    return ResponseEntity.noContent().build();
  }

}
