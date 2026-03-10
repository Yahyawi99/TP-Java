package com.example.blog.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String username;
  private String password;
  private String role;

  @OneToMany(mappedBy = "author")
  private List<Post> posts;

  // Getters and setters...
}