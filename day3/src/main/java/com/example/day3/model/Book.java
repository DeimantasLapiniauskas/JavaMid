package com.example.day3.model;

import jakarta.persistence.*;

@Entity // mandatory
@Table(name = "books") // recommended
public class Book {

  @Id // primary key
  @GeneratedValue(strategy = GenerationType.IDENTITY) // SQL tai yra AutoIncrement
  private long id; // Duombazėj: BIGINT

  private String title; // Duombazėj: VARCHAR
  private String author; // VARCHAR

  public Book(String title, String author) {
    this.title = title;
    this.author = author;
  }

  public Book() {
  }

  public long getId() {
    return id;
  }

//  public void setId(long id) { // it's autoincrement, gamer
//    this.id = id;
//  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getAuthor() {
    return author;
  }

  public void setAuthor(String author) {
    this.author = author;
  }
}
