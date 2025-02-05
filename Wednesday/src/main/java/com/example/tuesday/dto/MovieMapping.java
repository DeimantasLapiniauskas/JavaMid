package com.example.tuesday.dto;

import com.example.tuesday.model.Movie;

import java.awt.print.Book;
import java.util.List;

public class MovieMapping {

  //Return a list of movies
  public static List<MovieDTO> toMovieDTOList(List<Movie> movies) {
    return movies.stream()
            .map(movie -> new MovieDTO(
                            movie.getTitle(),
                            movie.getDirector(),
                            movie.getScreenings(),
                            movie.getActors()
                    )
            )
            .toList();
  }

  //Return a singular movie
  public static MovieDTO toMovieDTO(Movie movie) {
    return new MovieDTO(movie.getTitle(), movie.getDirector(), movie.getScreenings(), movie.getActors());
  }

  //
  public static Movie toMovie(MovieDTO movieDTO) {
    Movie movie = new Movie();
    movie.setActors(movieDTO.actors());
    movie.setDirector(movieDTO.director());
    movie.setScreenings(movieDTO.screenings());
    movie.setTitle(movieDTO.title());

    return movie;
  }
}
