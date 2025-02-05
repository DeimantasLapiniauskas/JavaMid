package com.example.tuesday.controller;

import com.example.tuesday.dto.MovieDTO;
import com.example.tuesday.dto.MovieMapping;
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
  public ResponseEntity<List<MovieDTO>> getAllBooks() {
    return ResponseEntity.ok(MovieMapping.toMovieDTOList(movieService.findAllMovies()));
  }

  @GetMapping("/movies/{id}")
    // get by ID
  ResponseEntity<MovieDTO> getMovieById(@PathVariable long id) {
    if (!movieService.findMovieByID(id)) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(MovieMapping.toMovieDTO(movieService.getMovieById(id).get()));
  }

  @GetMapping("/movies/pagination")
    // get by pages
  ResponseEntity<List<MovieDTO>> getMovieByPage(@RequestParam @Min(0) @Max(10) int page,
                                                @RequestParam int size,
                                                @RequestParam(required = false) String sort) {
    return ResponseEntity.ok(MovieMapping.toMovieDTOList(movieService.findAllMoviesByPage(page, size, sort).getContent()));
  }

  @PostMapping("/movies")
    // make shit up idfk
  ResponseEntity<MovieDTO> saveMovie(@Valid @RequestBody MovieDTO movieDTO) {
    movieService.saveMovie(MovieMapping.toMovie(movieDTO));
    return ResponseEntity.ok(movieDTO);
  }

  @PutMapping("/movies/{id}") // update by id. if id is free, make shit up idfk
  public ResponseEntity<MovieDTO> updateMovie(@PathVariable long id,
                                              @Valid @RequestBody MovieDTO movieDTO) {
    if (!movieService.findMovieByID(id)) {
      movieService.saveMovie(MovieMapping.toMovie(movieDTO));
      return ResponseEntity.created(
                      ServletUriComponentsBuilder.fromCurrentRequest()
                              .replacePath("/movies/{id}")
                              .buildAndExpand(id)
                              .toUri())
              .body(movieDTO);
    }

    Movie movieFromDB = movieService.getMovieById(id).get();

    movieFromDB.setTitle(movieDTO.title());
    movieFromDB.setDirector(movieDTO.director());
    movieFromDB.setScreenings(movieDTO.screenings());
    movieFromDB.setActors(movieDTO.actors());

    return ResponseEntity.ok(MovieMapping.toMovieDTO(movieService.saveMovie(movieFromDB)));
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
