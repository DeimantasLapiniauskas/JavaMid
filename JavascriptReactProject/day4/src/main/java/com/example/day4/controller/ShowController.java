package com.example.day4.controller;

import com.example.day4.model.Show;
import com.example.day4.service.ShowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class ShowController {

  private final ShowService showService;

  @Autowired
  public ShowController(ShowService showService) {
    this.showService = showService;
  }


  @GetMapping("/home")
  public ResponseEntity<List<Show>> getAllShows(@RequestParam Optional<String> search) {
    if (search.isEmpty()) {
      return ResponseEntity.ok(showService.findAllShows());
    }
    return ResponseEntity.ok(showService.findAllShowsByTitle(search.get()));
  }

  @GetMapping("/movies")
  public ResponseEntity<List<Show>> getAllMovies(@RequestParam Optional<String> search) {
    if (search.isEmpty()) {
      return ResponseEntity.ok(showService.findAllShowsByCategory("Movie"));
    }
    return ResponseEntity.ok(showService.findByTitleContainingAndCategory(search.get(), "Movie"));
  }

  @GetMapping("/tvseries")
  public ResponseEntity<List<Show>> getAllTvShows(@RequestParam Optional<String> search) {
    if (search.isEmpty()) {
      return ResponseEntity.ok(showService.findAllShowsByCategory("TV Series"));
    }
    return ResponseEntity.ok(showService.findByTitleContainingAndCategory(search.get(), "TV Series"));
  }

  @GetMapping("/bookmarked")
  public ResponseEntity<List<Show>> getAllBookmarked(@RequestParam Optional<String> search) {
    if (search.isEmpty()) {
      return ResponseEntity.ok(showService.findAllShowsByBookmarked());
    }
    return ResponseEntity.ok(showService.findByTitleContainingAndBookmarked(search.get()));
  }
}
