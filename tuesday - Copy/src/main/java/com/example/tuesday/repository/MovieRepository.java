package com.example.tuesday.repository;

import com.example.tuesday.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;


public interface MovieRepository extends JpaRepository<Movie, Long> {


}
