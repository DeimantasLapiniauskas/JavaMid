package com.example.Running.Club.dto;

import com.example.Running.Club.model.Registration;

import java.util.List;

public class RegistrationMapping {

  public static List<RegistrationResponseDTO> toRegistrationDTOList(List<Registration> registrations) {
    return registrations.stream()
            .map(registration -> new RegistrationResponseDTO(
                            registration.getId(),
                            registration.getUserId(),
                            registration.getRunningEvent().getName(),
                            registration.getRegistrationDate()
                    )
            )
            .toList();
  }

  public static Registration toRegistration(RegistrationRequestDTO registrationRequestDTO) {
    Registration registration = new Registration();
    registration.setUser(registrationRequestDTO.user());
    return registration;
  }

  public static RegistrationResponseDTO toRegistrationResponseDTO(Registration registration) {
    return new RegistrationResponseDTO(registration.getId(), registration.getUserId(), registration.getRunningEvent().getName(), registration.getRegistrationDate());
  }

}
