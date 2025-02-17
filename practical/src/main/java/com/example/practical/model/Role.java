package com.example.practical.model;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;

@Entity
@Table(name = "roles")
public class Role implements GrantedAuthority {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  private String name;

  public Role(String name) {
    this.name = name;
  }

  public Role(Long id) {
    this.id = id;
  }

  public Role() {
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public long getId() {
    return id;
  }

  @Override
  public String getAuthority() {
    return name;
  }
}
