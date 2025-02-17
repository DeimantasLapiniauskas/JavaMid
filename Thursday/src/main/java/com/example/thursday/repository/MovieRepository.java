package com.example.thursday.repository;

import com.example.thursday.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;


public interface MovieRepository extends JpaRepository<Movie, Long> {
  boolean existsById(Long id);

  boolean existsMovieByDirector(String director);

  boolean existsMovieByTitle(String title);

  boolean existsMovieByDirectorAndIdNot(String director, Long id);

  boolean existsMovieByTitleAndIdNot(String title, Long id);

}
