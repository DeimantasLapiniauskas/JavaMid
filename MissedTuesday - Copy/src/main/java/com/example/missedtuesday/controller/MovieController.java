package com.example.missedtuesday.controller;

import com.example.missedtuesday.dto.MovieDTO;
import com.example.missedtuesday.dto.MovieMapping;
import com.example.missedtuesday.model.Movie;
import com.example.missedtuesday.service.MovieService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.security.Principal;
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

  @GetMapping({"/movies", "/getMovies"}) //basic get all
  public ResponseEntity<List<MovieDTO>> getAllMovies() {
    return ResponseEntity.ok(MovieMapping.toMovieDTOList(movieService.findAllMovies()));
  }

  @GetMapping({"/movies/{id}", "/getMovie/{id}"})
    // get by ID
  ResponseEntity<MovieDTO> getMovieById(@PathVariable long id) {
    if (!movieService.existsMovieByID(id)) {
      return ResponseEntity.notFound().build();
    }

    return ResponseEntity.ok(MovieMapping.toMovieDTO(movieService.getMovieById(id).get()));
  }

  @GetMapping({"/movies/pagination", "/getMoviePaged"})
    // get by pages
  ResponseEntity<List<MovieDTO>> getMovieByPage(@RequestParam @Min(0) @Max(10) int page,
                                                @RequestParam int size,
                                                @RequestParam(required = false) String sort) {
    return ResponseEntity.ok(MovieMapping
            .toMovieDTOList(
                    movieService
                            .findAllMoviesByPage(
                                    page, size, sort
                            )
                            .getContent()));
  }

  @PostMapping({"/movies", "/addMovie"})
    // make shit up idfk
  ResponseEntity<?> saveMovie(@Valid @RequestBody MovieDTO movieDTO) {
    if (movieService.existsMovieByTitle(movieDTO.title()) &&
            movieService.existsMovieByDirector(movieDTO.director())
    ) {
      return ResponseEntity
              .status(HttpStatus.BAD_REQUEST)
              .body("Movie with that title and director already exists");
    }
    Movie savedMovie = movieService.saveMovie(MovieMapping.toMovie(movieDTO));

    return ResponseEntity.created(
            ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(savedMovie.getId())
                    .toUri()
    ).body(movieDTO);
  }

  @PutMapping({"/movies/{id}", "/updateMovie/{id}"})
  // update by id. if id is free, make shit up idfk
  public ResponseEntity<?> updateMovie(@PathVariable long id,
                                       @Valid @RequestBody MovieDTO movieDTO,
                                       Principal principal) {


    if (movieService.existsMovieByTitleAndNotId(movieDTO.title(), id) &&
            movieService.existsMovieByDirectorAndNotId(movieDTO.director(), id)) {
      return ResponseEntity
              .status(HttpStatus.BAD_REQUEST)
              .body("Movie with that title and director already exists");
    }

    if (!movieService.existsMovieByID(id)) {
      movieService.saveMovie(MovieMapping.toMovie(movieDTO));
      return ResponseEntity.created(
                      ServletUriComponentsBuilder.fromCurrentRequest()
                              .replacePath("/movies/{id}")
                              .buildAndExpand(id)
                              .toUri())
              .body(movieDTO);
    }

    Movie movieFromDB = movieService.getMovieById(id).get();

    MovieMapping.updateMovieFromDTO(movieFromDB, movieDTO);

    return ResponseEntity.ok(MovieMapping.toMovieDTO(movieService.saveMovie(movieFromDB)));
  }

  @DeleteMapping({"/movies/{id}", "/deleteMovie/{id}"}) //kill
  public ResponseEntity<Void> deleteMovie(@PathVariable long id) {
    if (!movieService.existsMovieByID(id)) {
      return ResponseEntity.notFound().build();
    }

    movieService.deleteMovieById(id);

    return ResponseEntity.noContent().build();
  }

}
