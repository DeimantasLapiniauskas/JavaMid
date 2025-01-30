package com.example.day3.service;

import com.example.day3.model.Book;
import com.example.day3.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

  private final BookRepository bookRepository;

  @Autowired
  public BookService(BookRepository bookRepository) {
    this.bookRepository = bookRepository;
  }

  public List<Book> findAllBooks() {
    return bookRepository.findAll();
  }

  public Optional<Book> findBookById(long id) {
    return bookRepository.findById(id);
  }

  public boolean existsBookById(long id) {
    return bookRepository.existsById(id);
  }

  public Book saveBook(Book book) {
    bookRepository.save(book);
    return book;
  }

  public void deleteBookById(Long id) {
    bookRepository.deleteById(id);
  }

  // derived query
  public boolean existsBookByTitle(String title) {
    return bookRepository.existsByTitle(title);
  }

  public Book findBookByTitle(String title) {
    return bookRepository.findByTitle(title);
  }

}
