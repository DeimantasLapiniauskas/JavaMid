package com.example.day3.repository;

import com.example.day3.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BookRepository extends JpaRepository<Book, Long> { // <Object, primary key type>
  boolean existsByTitle(String title);

  Book findByTitle(String title);
}
