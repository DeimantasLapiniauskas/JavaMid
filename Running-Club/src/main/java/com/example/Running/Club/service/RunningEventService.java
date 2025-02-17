package com.example.Running.Club.service;


import com.example.Running.Club.dto.RunningEventResponseDTO;
import com.example.Running.Club.model.RunningEvent;
import com.example.Running.Club.repository.RunningEventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RunningEventService {

  private final RunningEventRepository runningEventRepository;

  public boolean existsEventById(long id) {
    return runningEventRepository.existsById(id);
  }

  ;

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
}
