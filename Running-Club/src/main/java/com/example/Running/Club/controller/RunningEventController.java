package com.example.Running.Club.controller;


import com.example.Running.Club.dto.*;
import com.example.Running.Club.model.Registration;
import com.example.Running.Club.model.RunningEvent;
import com.example.Running.Club.model.User;
import com.example.Running.Club.service.RegistrationService;
import com.example.Running.Club.service.RunningEventService;
import com.example.Running.Club.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/events")
public class RunningEventController {

  private final RunningEventService runningEventService;
  private final RegistrationService registrationService;
  private final UserService userService;


  @Autowired
  public RunningEventController(RunningEventService runningEventService,
                                RegistrationService registrationService,
                                UserService userService) {
    
    this.runningEventService = runningEventService;
    this.registrationService = registrationService;
    this.userService = userService;
  }

  @PostMapping
  public ResponseEntity<RunningEventResponseDTO> addEvent(@Valid @RequestBody RunningEventRequestDTO runningEventRequestDTO) {

    RunningEvent runningEvent = RunningEventMapping.toRunningEvent(runningEventRequestDTO);
    runningEvent = runningEventService.saveEvent(runningEvent);

    return ResponseEntity.status(HttpStatus.CREATED)
            .body(RunningEventMapping.toResponseDTO(runningEvent));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteEvent(@PathVariable long id) {

    if (!runningEventService.existsEventById(id)) {
      return ResponseEntity.notFound().build();
    }

    runningEventService.deleteEventById(id);

    return ResponseEntity.noContent().build();

  }

  @GetMapping
  public ResponseEntity<List<RunningEventResponseDTO>> getAllEvents() {
    return ResponseEntity.ok(RunningEventMapping.toEventDTOList(runningEventService.getAllEvents()));
  }


  @PostMapping("/{eventId}/register")
  public ResponseEntity<?> registerEvent(@PathVariable long eventId,
                                         @RequestBody RegistrationRequestDTO registrationRequestDTO,
                                         Principal principal) {

    if (!runningEventService.existsEventById(eventId)) {
      return ResponseEntity.notFound().build();
    }

    final User currentUser = userService.findUserByUsername(principal.getName()).get();
    if (!Objects.equals(registrationRequestDTO.user().getId(), currentUser.getId())) { // Check if the user being registered is the user logged in
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("You can only register yourself to an event!");
    }

    if (registrationService.existsByUserIdAndEventId(registrationRequestDTO.user().getId(), eventId)) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("You're already registered for this event!");
    }

    Registration savedRegistration = registrationService.saveRegistration(
            new Registration(
                    registrationRequestDTO.user().getId(),
                    eventId,
                    LocalDateTime.now()
            )
    );

    return ResponseEntity.status(HttpStatus.CREATED).body(RegistrationMapping.toRegistrationResponseDTO(savedRegistration, runningEventService.getEventById(eventId).get().getName()));
  }

  @GetMapping("/{eventId}/participants")
  public ResponseEntity<List<UserResponseListableDTO>> getEventParticipants(@PathVariable long eventId) {

    if (!runningEventService.existsEventById(eventId)) {
      return ResponseEntity.notFound().build();
    }

    return ResponseEntity.ok(UserMapping.toUserDTOList(
                    registrationService.getByEventId(eventId).stream()
                            .filter(event -> event.getId() == eventId)
                            .map(Registration::getUser)
                            .toList()
            )
    );
  }
}
