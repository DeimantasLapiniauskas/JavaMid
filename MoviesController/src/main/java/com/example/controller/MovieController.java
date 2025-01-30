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
@RestController // marked as able to give instructions
public class MovieController {
  List<Movie> movies = new ArrayList<>(
          List.of(
                  new Movie(4, "fuck", "me"),
                  new Movie(69, "why", "hate")
          )
  );

  @GetMapping("/movies") // only be a thing at localhost:xXxX/movies
  public ResponseEntity<List<Movie>> getMovies() {
    return ResponseEntity.ok().body(movies); // 200
  }

  @GetMapping("/movies/{id}")
  public ResponseEntity<Movie> getMovie(@PathVariable int id) { // {id} + @PathVariable id allows getting what was typed as id as a variable
    return ResponseEntity.ok().body( // 200
            movies.stream()
                    .filter(movie -> movie.getId() == id)
                    .findFirst()
                    .orElse(null)
    );
  }

  @GetMapping("/movies/search")
  public ResponseEntity<List<Movie>> searchMovies(@RequestParam String title) { // @RequestParams + title allows getting what was typed as ?title=******* as a variable. Variable name can vary.
    return ResponseEntity.ok().body( // 200
            movies.stream()
                    .filter(movie ->
                            Objects.equals(movie.getTitle(), title) // Apparently, checking if string == string is a bit too complicated for java normally.
                    )
                    .collect(Collectors.toList())
    );
  }

  @PostMapping("/movies")
  public ResponseEntity<Movie> addMovie(@RequestBody Movie movie) {
    if (movies.contains(movie)) {
      return ResponseEntity.badRequest().build(); // 400
    }
    // teach told us to only do the intended behaviour AFTER checking if it passes all required checks.
    movies.add(movie);
    return ResponseEntity.created( // 201
                    ServletUriComponentsBuilder // starts constructing a new link
                            .fromCurrentRequest()
                            .path(
                                    String.valueOf(movie.getId())  // adds movie/{id} link to localhost
                            )
                            .build()
                            .toUri()
            )
            .body(movie);
  }
}
