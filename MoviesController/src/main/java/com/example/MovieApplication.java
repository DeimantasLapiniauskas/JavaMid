package com.example;

import com.example.controller.MovieController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication // mark as main
public class MovieApplication {
  public static void main(String[] args) {
    SpringApplication.run(MovieController.class, args); // where to look for instructions
  }
}
