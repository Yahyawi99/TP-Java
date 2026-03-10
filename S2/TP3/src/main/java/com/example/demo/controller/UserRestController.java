package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.services.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserRestController {

  private final UserService userService;

  public UserRestController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping
  public List<User> getAllUsers() {
    return userService.findAll();
  }

  @GetMapping("/{id}")
  public User getUserById(@PathVariable Long id) {
    return userService.findById(id);
  }

  @PostMapping
  public User createUser(@RequestBody User user) {
    return userService.save(user);
  }

  @DeleteMapping("/{id}")
  public void deleteUser(@PathVariable Long id) {
    userService.delete(id);
  }
}