package com.example.thursday.dto;

import com.example.thursday.model.Movie;

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

  public static Movie toMovie(MovieDTO movieDTO) {
    Movie movie = new Movie();
    updateMovieFromDTO(movie, movieDTO);

    return movie;
  }

  //uses setters of movie to be identical to movieDTO
  public static void updateMovieFromDTO(Movie movie, MovieDTO movieDTO) {
    movie.setTitle(movieDTO.title());
    movie.setDirector(movieDTO.director());
    movie.setScreenings(movieDTO.screenings());
    movie.setActors(movieDTO.actors());
  }

}
