package com.example.day3.controller;

import com.example.day3.model.Book;
import com.example.day3.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.Optional;


@RestController
public class BookController {

  // @Autowired // field injection
  private final BookService bookService;

  @Autowired // constructor injector (Both work, but this one's better)
  public BookController(BookService bookService) {
    this.bookService = bookService;
  }

  @GetMapping("/books")
  public ResponseEntity<List<Book>> getAllBooks() {
    return ResponseEntity.ok(bookService.findAllBooks());
  }

  @GetMapping("/books/{id}")
  public ResponseEntity<Book> getBookById(@PathVariable long id) {
    Optional<Book> foundBook = bookService.findBookById(id);
    if (foundBook.isEmpty()) {
//      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ID not found"); // unnecessary
      return ResponseEntity.notFound().build();
    }

    return ResponseEntity.ok(foundBook.get());
  }

  @PostMapping("/books")
  public ResponseEntity<?> saveBook(@RequestBody Book book) {
    if (book.getTitle().isEmpty() || book.getAuthor().isEmpty()) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Title or author cannot be empty");
    }

    Book savedBook = bookService.saveBook(book);

    return ResponseEntity.created(
                    ServletUriComponentsBuilder.fromCurrentRequest()
                            .path("/{id}")
                            .buildAndExpand(savedBook.getId())
                            .toUri())
            .body(book);
  }

  @PutMapping("/books/{id}")
  public ResponseEntity<?> updateBook(@PathVariable long id, @RequestBody Book book) {
    if (book.getTitle().isEmpty() || book.getAuthor().isEmpty()) { // if bad request
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Title or author cannot be empty");
    }

    if (bookService.existsBookById(id)) { // if already exists with that ID
      Book bookFromDb = bookService.findBookById(id).get();

      bookFromDb.setTitle(book.getTitle());
      bookFromDb.setAuthor(book.getAuthor());

      return ResponseEntity.ok(bookService.saveBook(bookFromDb));
    }
    // otherwise, make up a new one lol lmao
    Book savedBook = bookService.saveBook(book);
    return ResponseEntity.created(
                    ServletUriComponentsBuilder.fromCurrentRequest()
                            .replacePath("/api/books/{id}")// gotta use replacePath or else the link glitches
                            .buildAndExpand(savedBook.getId())
                            .toUri())
            .body(book);
  }

  @DeleteMapping("/books/{id}")
  public ResponseEntity<Void> deleteBook(@PathVariable long id) {
    if (!bookService.existsBookById(id)) {
      return ResponseEntity.notFound().build();
    }

    bookService.deleteBookById(id);
    return ResponseEntity.noContent().build();
  }

  @GetMapping("/books/search")
  public ResponseEntity<Book> getBookByTitle(@RequestParam String title) {

    for (Book book : bookService.findAllBooks()) {           //go through the entire database, to
      if (!bookService.existsBookByTitle(book.getTitle())) { // check if this thing even exists.
        return ResponseEntity.notFound().build();            // If not, 404?
      }
    }

    return ResponseEntity.ok(bookService.findBookByTitle(title));
  }
}