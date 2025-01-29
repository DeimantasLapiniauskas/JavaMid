package com.example.demon;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.awt.print.Book;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api") // Pridedame priešdėlį
public class HelloController {
  @GetMapping("/books")
  public String getBooks() {
    return "Hello, Spring Web";
  }
}