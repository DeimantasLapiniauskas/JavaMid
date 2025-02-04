package com.example.tuesday.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "screenings")
public class Screening {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;
  private String theatreName;
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