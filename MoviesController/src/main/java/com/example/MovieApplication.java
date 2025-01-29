package com.example;

import com.example.controller.MovieController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MovieApplication {
  public static void main(String[] args) {
    SpringApplication.run(MovieController.class, args);
  }
}
