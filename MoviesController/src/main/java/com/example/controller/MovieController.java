package com.example.controller;

import com.example.model.Movie;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@SpringBootApplication
@RestController
public class MovieController {
  List<Movie> movies = new ArrayList<>(
          List.of(
                  new Movie(4, "fuck", "me"),
                  new Movie(69, "why", "hate")
          )
  );

  @GetMapping("/movies")
  public ResponseEntity<List<Movie>> getMovies() {
    return ResponseEntity.ok().body(movies);
  }

  @GetMapping("/movies/{id}")
  public ResponseEntity<Movie> getMovie(@PathVariable int id) {
    return ResponseEntity.ok().body(movies.stream().filter(movie -> movie.getId() == id).findFirst().orElse(null));
  }

  @GetMapping("/movies/search")
  public ResponseEntity<List<Movie>> searchMovies(@RequestParam String title) {
    return ResponseEntity.ok().body(movies.stream()
            .filter(movie -> Objects
                    .equals(
                            movie.getTitle(), title
                    )
            )
            .collect(Collectors.toList()));
//            movies.stream()
//            .filter(movie -> Objects
//                    .equals(
//                            movie.getTitle(), title
//                    )
//            )
//            .collect(Collectors.toList());
  }

  @PostMapping("/movies")
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
