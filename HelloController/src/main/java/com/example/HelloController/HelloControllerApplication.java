package com.example.HelloController;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class HelloControllerApplication {
  public static void main(String[] args) {
    SpringApplication.run(HelloControllerApplication.class, args);
  }

  @GetMapping("/hello")
  public String sayHello() {
    return "Hello, Spring Web";
  }

  @GetMapping("/hello/{index}")
  public ResponseEntity<String> putHello(@PathVariable String index) {
    return ResponseEntity.ok("Hello, " + index);
  }
}