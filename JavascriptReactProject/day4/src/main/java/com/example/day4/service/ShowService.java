package com.example.day4.service;

import com.example.day4.model.Show;
import com.example.day4.repository.ShowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShowService {

  private final ShowRepository showRepository;

  @Autowired
  public ShowService(ShowRepository showRepository) {
    this.showRepository = showRepository;
  }

  public List<Show> findAllShows() {
    return showRepository.findAll();
  }

  public List<Show> findAllShowsByTitle(String title) {
    return showRepository.findByTitleContaining(title);
  }

  public List<Show> findAllShowsByCategory(String category) {
    return showRepository.findByCategory(category);
  }


  public List<Show> findAllShowsByBookmarked() {
    return showRepository.findByIsBookmarked();
  }

  public List<Show> findByTitleContainingAndCategory(String title, String category) {
    return showRepository.findByTitleContainingAndCategory(title, category);
  }

  public List<Show> findByTitleContainingAndBookmarked(String title) {
    return showRepository.findByTitleContainingAndIsBookmarked(title);
  }

}
