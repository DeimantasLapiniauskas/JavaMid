//package com.example.Running.Club.controller;
//
//import com.example.Running.Club.dto.RegistrationMapping;
//import com.example.Running.Club.dto.RunningEventResponseDTO;
//import com.example.Running.Club.service.RegistrationService;
//import com.example.Running.Club.service.RunningEventService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/api")
//public class RegistrationController {
//
//  private final RunningEventService runningEventService;
//  private final RegistrationService registrationService;
//
//  @Autowired
//  public RegistrationController(RunningEventService runningEventService, RegistrationService registrationService) {
//    this.runningEventService = runningEventService;
//    this.registrationService = registrationService;
//  }
//
//  @PostMapping("/events/{eventId}/register")
//  public ResponseEntity<RunningEventResponseDTO> registerEvent(@PathVariable long eventId) {
//    if (!runningEventService.existsEventById(eventId)) {
//      return ResponseEntity.notFound().build();
//    }
//
//
////    return ResponseEntity.ok(RegistrationMapping.toRegistrationDTOList(registrationService.getAll()))
//  }
//}
