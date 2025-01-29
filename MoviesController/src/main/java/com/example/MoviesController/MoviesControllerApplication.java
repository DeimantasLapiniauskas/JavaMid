package com.example.MoviesController;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@RestController
public class MoviesControllerApplication {
  List<Movie> movies = new ArrayList<>(
          List.of(
                  new Movie(4, "fuck", "me"),
                  new Movie(69, "why", "hate")
          )
  );

  public static void main(String[] args) {
    SpringApplication.run(MoviesControllerApplication.class, args);

  }

  @GetMapping("/movies")
  public List<Movie> getMovies() {
    return movies;
  }

  @GetMapping("/movies/{id}")
  public Movie getMovie(@PathVariable int id) {
    return movies.stream().filter(movie -> movie.getId() == id).findFirst().orElse(null);
  }

  @PostMapping("movies")
  public ResponseEntity<Movie> addMovie(@RequestBody Movie movie) {
    if (movies.contains(movie)) {
      return ResponseEntity.badRequest().build();

    }
    movies.add(movie);
    return ResponseEntity.created(
                    ServletUriComponentsBuilder
                            .fromCurrentRequest()
                            .path(String.valueOf(movie.getId()))
                            .build()
                            .toUri())
            .body(movie);
  }

}
