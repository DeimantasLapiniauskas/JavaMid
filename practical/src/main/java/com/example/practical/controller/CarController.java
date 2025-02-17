package com.example.practical.controller;


import com.example.practical.model.Car;
import com.example.practical.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/cars")
public class CarController {

  private final CarService carService;

  @Autowired
  public CarController(CarService carService) {
    this.carService = carService;
  }

  @GetMapping
  public ResponseEntity<List<Car>> getAllCars() {
    return ResponseEntity.ok(carService.findAllCars());
  }


}
