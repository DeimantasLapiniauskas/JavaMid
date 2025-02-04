package com.example.tuesday.service;

import com.example.tuesday.model.Movie;
import com.example.tuesday.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovieService {

  private final MovieRepository movieRepository;

  @Autowired
  public MovieService(MovieRepository movieRepository) {
    this.movieRepository = movieRepository;
  }

  public List<Movie> findAllMovies() {
    return movieRepository.findAll();
  }


  public Optional<Movie> getMovieById(long id) {
    return movieRepository.findById(id);
  }

  public Movie saveMovie(Movie movie) {
    movieRepository.save(movie);
    return movie;
  }

  public boolean findMovieByID(long id) {
    return movieRepository.findById(id).isPresent();
  }

  public void deleteMovieById(long id) {
    movieRepository.deleteById(id);
  }
}
