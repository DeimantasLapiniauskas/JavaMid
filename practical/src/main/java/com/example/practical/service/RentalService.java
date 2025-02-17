package com.example.practical.service;


import com.example.practical.repository.RentalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RentalService {

  private final RentalRepository rentalRepository;

  @Autowired
  public RentalService(RentalRepository rentalRepository) {
    this.rentalRepository = rentalRepository;
  }

}
