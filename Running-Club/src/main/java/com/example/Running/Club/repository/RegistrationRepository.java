package com.example.Running.Club.repository;

import com.example.Running.Club.model.Registration;
import com.example.Running.Club.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RegistrationRepository extends JpaRepository<Registration, Long> {
  boolean existsByUserIdAndRunningEventId(long userId, long runningEventId);

  List<Registration> findByRunningEventId(long runningEventId);
}
