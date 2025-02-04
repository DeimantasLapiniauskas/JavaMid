package com.example.tuesday.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "actors")
public class Actor {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Size(min = 2, max = 50, message = "Field has to be between 2 and 50 characters long.")
  private String your;

  @Pattern(
          regexp = "^[A-Z][a-z]+$",
          message = "Liking must start with a capital letter, and" +
                  " be followed only by (at least one) non-capital letters")
  private String liking;

  public Actor(String your, String liking) {
    this.your = your;
    this.liking = liking;
  }

  public Actor() {
  }

  public long getId() {
    return id;
  }

  public String getYour() {
    return your;
  }

  public void setYour(String your) {
    this.your = your;
  }

  public String getLiking() {
    return liking;
  }

  public void setLiking(String liking) {
    this.liking = liking;
  }
}
