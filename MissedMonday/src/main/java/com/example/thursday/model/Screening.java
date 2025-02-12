package com.example.thursday.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

@Entity
@Table(name = "screenings")
public class Screening {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @NotNull
  @Size(min = 5, max = 50, message = "Field has to be between 2 and 50 characters long.")
  private String theatreName;

  @NotNull
  @Future(message = "Date and time must be in the future.")
  private LocalDateTime dateAndTime;

  public Screening(String TheatreName, LocalDateTime dateAndTime) {
    this.dateAndTime = dateAndTime;
    this.theatreName = TheatreName;
  }

  public Screening() {
  }

  public long getId() {
    return id;
  }

  public String getTheatreName() {
    return theatreName;
  }

  public void setTheatreName(String theatreName) {
    this.theatreName = theatreName;
  }

  public LocalDateTime getDateAndTime() {
    return dateAndTime;
  }

  public void setDateAndTime(LocalDateTime dateAndTime) {
    this.dateAndTime = dateAndTime;
  }
}