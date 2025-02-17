package com.example.Running.Club.model;


import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "running_events")
public class RunningEvent {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  private String name;
  private LocalDate calendar_date;
  private String location;
  private int maxParticipants;

  public RunningEvent() {
  }

  public long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public LocalDate getCalendar_date() {
    return calendar_date;
  }

  public void setCalendar_date(LocalDate calendar_date) {
    this.calendar_date = calendar_date;
  }

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  public int getMaxParticipants() {
    return maxParticipants;
  }

  public void setMaxParticipants(int maxParticipants) {
    this.maxParticipants = maxParticipants;
  }
}
