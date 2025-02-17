package com.example.Running.Club.dto;

import com.example.Running.Club.model.RunningEvent;

import java.util.List;

public class RunningEventMapping {

  public static List<RunningEventResponseDTO> toEventDTOList(List<RunningEvent> runningEvents) {
    return runningEvents.stream()
            .map(event -> new RunningEventResponseDTO(
                            event.getId(),
                            event.getName(),
                            event.getCalendar_date(),
                            event.getLocation(),
                            event.getMaxParticipants()
                    )
            )
            .toList();
  }

  public static RunningEvent toRunningEvent(RunningEventRequestDTO runningEventRequestDTO) {
    RunningEvent runningEvent = new RunningEvent();
    runningEvent.setName(runningEventRequestDTO.name());
    runningEvent.setCalendar_date(runningEventRequestDTO.calendarDate());
    runningEvent.setLocation(runningEventRequestDTO.location());
    runningEvent.setMaxParticipants(runningEventRequestDTO.maxParticipants());
    return runningEvent;
  }

  public static RunningEventResponseDTO toResponseDTO(RunningEvent runningEvent) {
    return new RunningEventResponseDTO(runningEvent.getId(), runningEvent.getName(), runningEvent.getCalendar_date(), runningEvent.getLocation(), runningEvent.getMaxParticipants());
  }
}
