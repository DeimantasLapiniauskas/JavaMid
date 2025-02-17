package com.example.practical.model;


import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "rentals")
public class Rental {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @ManyToOne
  @JoinColumn(name = "user_id", insertable = false, updatable = false)
  private User user;

  @Column(name = "user_id")
  private long userId;

  @ManyToOne
  @JoinColumn(name = "car_id", insertable = false, updatable = false)
  private Car car;

  @Column(name = "car_id")
  private long carId;

  private LocalDate rental_start;

  private LocalDate rental_end;

  private BigDecimal price;


}
