package com.example.tuesday.controller;

import com.example.tuesday.model.Movie;
import com.example.tuesday.service.MovieService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RestController
public class MovieController {

  // why the fuck does ctrl+alt+l auto-format some comments to a new line,
  // but not all of them???

  private final MovieService movieService;

  @Autowired
  public MovieController(MovieService movieService) {
    this.movieService = movieService;
  }

  @GetMapping("/movies") //basic get all
  public ResponseEntity<List<Movie>> getAllBooks() {
    return ResponseEntity.ok(movieService.findAllMovies());
  }

  @GetMapping("/movies/{id}")
    // get by ID
  ResponseEntity<Movie> getMovieById(@PathVariable long id) {
    if (!movieService.findMovieByID(id)) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(movieService.getMovieById(id).get());
  }

  @GetMapping("/movies/pagination")
    // get by pages
  ResponseEntity<List<Movie>> getMovieByPage(@RequestParam @Min(0) @Max(10) int page,
                                             @RequestParam int size,
                                             @RequestParam(required = false) String sort) {
    return ResponseEntity.ok(movieService.findAllMoviesByPage(page, size, sort).getContent());
  }

  @PostMapping("/movies")
    // make shit up idfk
  ResponseEntity<Movie> saveMovie(@Valid @RequestBody Movie movie) {
    movieService.saveMovie(movie);
    return ResponseEntity.ok(movie);
  }

  @PutMapping("/movies/{id}") // update by id. if id is free, make shit up idfk
  public ResponseEntity<Movie> updateMovie(@PathVariable long id, @Valid @RequestBody Movie movie) {
    if (!movieService.findMovieByID(id)) {
      movieService.saveMovie(movie);
      return ResponseEntity.created(
                      ServletUriComponentsBuilder.fromCurrentRequest()
                              .replacePath("/movies/{id}")
                              .buildAndExpand(movie.getId())
                              .toUri())
              .body(movie);
    }

    Movie movieFromDB = movieService.getMovieById(id).get();

    movieFromDB.setTitle(movie.getTitle());
    movieFromDB.setDirector(movie.getDirector());
    movieFromDB.setScreenings(movie.getScreenings());
    movieFromDB.setActors(movie.getActors());

    return ResponseEntity.ok(movieService.saveMovie(movieFromDB));
  }

  @DeleteMapping("/movies/{id}") //kill
  public ResponseEntity<Void> deleteMovie(@PathVariable long id) {
    if (!movieService.findMovieByID(id)) {
      return ResponseEntity.notFound().build();
    }

    movieService.deleteMovieById(id);
    return ResponseEntity.noContent().build();
  }
}
