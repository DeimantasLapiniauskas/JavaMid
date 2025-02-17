package com.example.thursday.controller;

import com.example.thursday.dto.MovieDTO;
import com.example.thursday.dto.MovieMapping;
import com.example.thursday.model.Movie;
import com.example.thursday.model.User;
import com.example.thursday.service.MovieService;
import com.example.thursday.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.security.Principal;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
public class MovieController {

  // why the fuck does ctrl+alt+l auto-format some comments to a new line,
  // but not all of them???

  private final MovieService movieService;
  private final UserService userService;


  @Autowired
  public MovieController(MovieService movieService, UserService userService) {
    this.movieService = movieService;
    this.userService = userService;
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

    movieService.saveMovie(MovieMapping.toMovie(movieDTO));

    return ResponseEntity.ok(movieDTO);
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
