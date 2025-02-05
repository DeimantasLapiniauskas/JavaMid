package com.example.tuesday.service;

import com.example.tuesday.model.Movie;
import com.example.tuesday.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

  public Page<Movie> findAllMoviesByPage(int page, int size, String sort) {
    Pageable pageable;
    if (sort == null) {
      pageable = PageRequest.of(page, size);
    } else {
      pageable = PageRequest.of(page, size, Sort.by(sort));
    }
    return movieRepository.findAll(pageable);
  }
}
