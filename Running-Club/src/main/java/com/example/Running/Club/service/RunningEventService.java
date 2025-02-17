package com.example.Running.Club.service;


import com.example.Running.Club.model.RunningEvent;
import com.example.Running.Club.repository.RunningEventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RunningEventService {

  private final RunningEventRepository runningEventRepository;

  public boolean existsEventById(long id) {
    return runningEventRepository.existsById(id);
  }

  @Autowired
  public RunningEventService(RunningEventRepository runningEventRepository) {
    this.runningEventRepository = runningEventRepository;
  }

  public RunningEvent saveEvent(RunningEvent runningEvent) {
    return runningEventRepository.save(runningEvent);
  }

  public void deleteEventById(long id) {
    runningEventRepository.deleteById(id);
  }

  public List<RunningEvent> getAllEvents() {
    return runningEventRepository.findAll();
  }

  public Optional<RunningEvent> getEventById(long eventId) {
    return runningEventRepository.findById(eventId);
  }
}
