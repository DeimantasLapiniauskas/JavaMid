package com.example.Running.Club.model;


import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "registrations")
public class Registration {


  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;


  @ManyToOne
  @JoinColumn(name = "user_id", insertable = false, updatable = false)
  private User user;

  @Column(name = "user_id")
  private long userId;

  @ManyToOne
  @JoinColumn(name = "running_event_id", insertable = false, updatable = false)
  private RunningEvent runningEvent;


  @Column(name = "running_event_id")
  private long runningEventId;

  private LocalDateTime registrationDate;


  public Registration() {
  }

  public Registration(long userId,
                      long runningEventId,
                      LocalDateTime registrationDate) {
    this.userId = userId;
    this.runningEventId = runningEventId;
    this.registrationDate = registrationDate;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public RunningEvent getRunningEvent() {
    return runningEvent;
  }

  public void setRunningEvent(RunningEvent runningEvent) {
    this.runningEvent = runningEvent;
  }

  public long getId() {
    return id;
  }

  public long getUserId() {
    return userId;
  }

  public void setUserId(long userId) {
    this.userId = userId;
  }

  public long getRunningEventId() {
    return runningEventId;
  }

  public void setRunningEventId(long runningEventId) {
    this.runningEventId = runningEventId;
  }

  public LocalDateTime getRegistrationDate() {
    return registrationDate;
  }

  public void setRegistrationDate(LocalDateTime registrationDate) {
    this.registrationDate = registrationDate;
  }
}
