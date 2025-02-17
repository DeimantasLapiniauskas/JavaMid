package com.example.Running.Club.service;


import com.example.Running.Club.model.User;
import com.example.Running.Club.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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

  public boolean existsUserByUsername(String username) {
    return userRepository.existsByUsername(username);
  }

  public User saveUser(User user) {
    return userRepository.save(user);
  }
}
