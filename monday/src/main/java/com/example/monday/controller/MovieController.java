package com.example.monday.controller;

import com.example.monday.model.Movie;
import com.example.monday.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RestController
public class MovieController {

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
  ResponseEntity<Movie> getMovieById(@PathVariable long id) { // get by ID
    if (!movieService.findMovieByID(id)) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(movieService.getMovieById(id).get());
  }

  @PostMapping("/movies")
  ResponseEntity<Movie> saveMovie(@RequestBody Movie movie) { // make shit up idfk
    movieService.saveMovie(movie);
    return ResponseEntity.ok(movie);
  }

  @PutMapping("/movies/{id}") // update by id. if id is free, make shit up idfk
  public ResponseEntity<Movie> updateMovie(@PathVariable long id, @RequestBody Movie movie) {
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
