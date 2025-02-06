package com.example.thursday.controller;

import com.example.thursday.dto.UserDTO;
import com.example.thursday.dto.UserMapping;
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
  public ResponseEntity<List<UserDTO>> getAllUsers() {
    return ResponseEntity.ok(UserMapping.toUserDTOList(userService.findAllUsers()));
  }

  @GetMapping("/users/{id}")
  public ResponseEntity<UserDTO> getUserById(@PathVariable long id) {

    Optional<User> foundUser = userService.findUserById(id);
    if (foundUser.isEmpty()) {
      return ResponseEntity.notFound().build();
    }

    return ResponseEntity.ok(UserMapping.toUserDTO(foundUser.get()));
  }

  //todo: @valid
  @PostMapping("/users")
  public ResponseEntity<?> addUser(@Valid @RequestBody UserDTO userDTO) {
    if (userService.existsUserByUsername(userDTO.username())) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username taken, please choose another!");
    }

    User user = UserMapping.toUser(userDTO);
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    userService.saveUser(user);

    return ResponseEntity.status(HttpStatus.CREATED).body(userDTO);
  }

  @PutMapping("/users/{id}")
  public ResponseEntity<?> updateUser(@PathVariable long id, @Valid @RequestBody UserDTO userDTO) {
    if (userService.existsUserByUsernameAndNotId(userDTO.username(), id)) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username already exists!");
    }

    if (!userService.existsUserById(id)) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User not found!");
    }

    User userFromDB = userService.findUserById(id).get();
    userFromDB.setUsername(userDTO.username());
    userFromDB.setPassword(passwordEncoder.encode(userDTO.password()));

    //return userDTO, too
    return ResponseEntity.ok(userService.saveUser(userFromDB));
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
