package com.example.Running.Club.service;

import com.example.Running.Club.model.Registration;
import com.example.Running.Club.model.User;
import com.example.Running.Club.repository.RegistrationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class RegistrationService {

  private final RegistrationRepository registrationRepository;

  @Autowired
  public RegistrationService(RegistrationRepository registrationRepository) {
    this.registrationRepository = registrationRepository;
  }

  public List<Registration> getAll() {
    return registrationRepository.findAll();
  }

  public Registration saveRegistration(Registration registration) {
    return registrationRepository.save(registration);
  }

  public boolean existsByUserIdAndEventId(long id, long eventId) {
    return registrationRepository.existsByUserIdAndRunningEventId(id, eventId);
  }

  public List<Registration> getByEventId(long eventId) {
    return registrationRepository.findByRunningEventId(eventId);
  }
}
