package com.example.day4.repository;

import com.example.day4.model.Show;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;

import java.util.List;

public interface ShowRepository extends JpaRepository<Show, Long> {

  List<Show> findByTitleContaining(String title);

  List<Show> findByTitleContainingAndCategory(String title, String category);

  List<Show> findByCategory(String category);

  @NativeQuery(value = "select * from shows where is_bookmarked = 1")
  List<Show> findByIsBookmarked();

  @NativeQuery(value = "select * from shows where is_bookmarked = 1 and title like %?1% ")
  List<Show> findByTitleContainingAndIsBookmarked(String title);
}
