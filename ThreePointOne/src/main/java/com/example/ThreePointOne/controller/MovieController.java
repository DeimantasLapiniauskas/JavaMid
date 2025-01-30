package com.example.ThreePointOne.controller;

import com.example.ThreePointOne.model.Movie;
import com.example.ThreePointOne.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MovieController {

  private final MovieService movieService;

  @Autowired
  public MovieController(MovieService movieService) {
    this.movieService = movieService;
  }

  @GetMapping("/movies")
  public ResponseEntity<List<Movie>> getAllBooks() {
    return ResponseEntity.ok(movieService.findAllMovies());
  }


}
