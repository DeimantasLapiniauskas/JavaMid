package com.example.day4.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.List;

@Entity
@Table(name = "shows")
public class Show {

  @Id
  @GeneratedValue
  private long id;

  private String title;
  private String thumbnail;
  private int year;
  private String category;
  private String rating;
  private boolean isBookmarked = false;
  private boolean isTrending = false;

  public Show(String title, String thumbnail, int year, String category, String rating) {
    this.title = title;
    this.thumbnail = thumbnail;
    this.year = year;
    this.category = category;
    this.rating = rating;
  }

  public Show() {

  }

  public long getId() {
    return id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public int getYear() {
    return year;
  }

  public void setYear(int year) {
    this.year = year;
  }

  public String getThumbnail() {
    return thumbnail;
  }

  public void setThumbnail(String thumbnail) {
    this.thumbnail = thumbnail;
  }

  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
  }

  public String getRating() {
    return rating;
  }

  public void setRating(String rating) {
    this.rating = rating;
  }

  public boolean isBookmarked() {
    return isBookmarked;
  }

  public void setBookmarked(boolean bookmarked) {
    isBookmarked = bookmarked;
  }

  public boolean isTrending() {
    return isTrending;
  }

  public void setTrending(boolean trending) {
    isTrending = trending;
  }
}
