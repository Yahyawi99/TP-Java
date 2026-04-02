package com.example.demo.services;

import com.example.demo.model.User;
import java.util.List;
import com.example.demo.dao.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
  private final UserRepository userRepository;

  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public List<User> findAll() {
    return userRepository.findAll();
  }

  public User findById(Long id) {
    return userRepository.findById(id).orElse(null);
  }

  public User save(User user) {
    return userRepository.save(user);
  }

  public void delete(Long id) {
    userRepository.deleteById(id);
  }

  public boolean authenticate(String email, String password) {
    User user = userRepository.findAll().stream()
        .filter(u -> u.getEmail().equals(email) && u.getPassword().equals(password))
        .findFirst()
        .orElse(null);
    return user != null;
  }
}