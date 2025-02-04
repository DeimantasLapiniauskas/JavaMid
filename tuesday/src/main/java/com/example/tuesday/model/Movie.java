package com.example.tuesday.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.List;

@Entity
@Table(name = "movies")
public class Movie {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Size(min = 2, max = 100)
  @NotNull
  private String title;

  @NotNull
  @Size(max = 50)
  @Pattern(regexp = "^[A-Z][a-z]+$", message = "Name must start with a capital letter, and contain at least one other letter or space.")
  private String director;

  @OneToMany(cascade = CascadeType.ALL)
  @JoinColumn(name = "movie_id")
  private List<Screening> screenings;

  @ManyToMany(cascade = CascadeType.ALL)
  @JoinTable(
          name = "movies_actors",
          joinColumns = @JoinColumn(name = "movie_id"),
          inverseJoinColumns = @JoinColumn(name = "actor_id")
  )
  @NotEmpty
  private List<Actor> actors;

  public Movie() {
  }

  public Movie(String title, String director, List<Screening> screenings, List<Actor> actors) {
    this.title = title;
    this.director = director;
    this.screenings = screenings;
    this.actors = actors;
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

  public String getDirector() {
    return director;
  }

  public void setDirector(String director) {
    this.director = director;
  }

  public List<Screening> getScreenings() {
    return screenings;
  }

  public void setScreenings(List<Screening> screenings) {
    this.screenings = screenings;
  }

  public List<Actor> getActors() {
    return actors;
  }

  public void setActors(List<Actor> actors) {
    this.actors = actors;
  }
}
