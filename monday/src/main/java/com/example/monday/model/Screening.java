package com.example.monday.model;

import jakarta.persistence.*;

@Entity
@Table(name = "screenings")
public class Screening {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;
  private String date;

  public Screening(String date) {
    this.date = date;
  }

  public Screening() {
  }

  public String getDate() {
    return date;
  }

  public void setDate(String date) {
    this.date = date;
  }

  public long getId() {
    return id;
  }

}
