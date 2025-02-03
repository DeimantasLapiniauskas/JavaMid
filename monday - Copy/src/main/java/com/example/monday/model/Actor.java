package com.example.monday.model;

import jakarta.persistence.*;

@Entity
@Table(name = "actors")
public class Actor {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  private String your;
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
