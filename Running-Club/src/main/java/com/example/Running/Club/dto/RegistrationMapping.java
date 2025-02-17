package com.example.Running.Club.dto;

import com.example.Running.Club.model.Registration;

public class RegistrationMapping {

  public static RegistrationResponseDTO toRegistrationResponseDTO(Registration registration, String eventName) {
    return new RegistrationResponseDTO(
            registration.getId(),
            registration.getUserId(),
            eventName,
            registration.getRegistrationDate()
    );
  }

}
