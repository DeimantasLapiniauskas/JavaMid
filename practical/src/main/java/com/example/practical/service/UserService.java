package com.example.practical.service;

import com.example.practical.model.User;
import com.example.practical.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

  private final UserRepository userRepository;

  @Autowired
  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }


  public Optional<User> findUserByUsername(String username) {
    return userRepository.findByUsername(username);
  }
}
