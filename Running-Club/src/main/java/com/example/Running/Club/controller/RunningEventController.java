package com.example.Running.Club.controller;


import com.example.Running.Club.dto.RegistrationMapping;
import com.example.Running.Club.dto.RunningEventMapping;
import com.example.Running.Club.dto.RunningEventRequestDTO;
import com.example.Running.Club.dto.RunningEventResponseDTO;
import com.example.Running.Club.model.RunningEvent;
import com.example.Running.Club.service.RunningEventService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class RunningEventController {

  private final RunningEventService runningEventService;

  @Autowired
  public RunningEventController(RunningEventService runningEventService) {
    this.runningEventService = runningEventService;
  }

  @PostMapping("/events")
  public ResponseEntity<RunningEventResponseDTO> addEvent(@Valid @RequestBody RunningEventRequestDTO runningEventRequestDTO) {
    RunningEvent runningEvent = RunningEventMapping.toRunningEvent(runningEventRequestDTO);
    runningEventService.saveEvent(runningEvent);
    return ResponseEntity.status(HttpStatus.CREATED)
            .body(RunningEventMapping.toResponseDTO(runningEvent));
  }

  @DeleteMapping("/events/{id}") // todo: fix this. Currently throws a 403
  public ResponseEntity<Void> deleteEvent(@PathVariable long id) {
    if (!runningEventService.existsEventById(id)) {
      return ResponseEntity.notFound().build();
    }

    runningEventService.deleteEventById(id);

    return ResponseEntity.noContent().build();

  }

  @GetMapping("/events")
  public ResponseEntity<List<RunningEventResponseDTO>> getAllEvents() {
    return ResponseEntity.ok(RunningEventMapping.toEventDTOList(runningEventService.getAllEvents()));
  }


}
