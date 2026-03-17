package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/users")
public class UserController {

  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping("/login")
  public String showLoginForm() {
    return "users/login";
  }

  @GetMapping("/signup")
  public String showSignupForm(Model model) {
    model.addAttribute("user", new User());
    return "users/signup";
  }

  @PostMapping("/signup")
  public String signup(@ModelAttribute User user) {
    userService.save(user);
    return "redirect:/posts";
  }

  // For now, just redirect to posts after login
  @PostMapping("/login")
  public String login(@RequestParam String username, @RequestParam String email) {
    // Simple authentication - in real app, you'd check credentials
    User user = userService.findAll().stream()
        .filter(u -> u.getUsername().equals(username) && u.getEmail().equals(email))
        .findFirst()
        .orElse(null);

    if (user != null) {
      return "redirect:/posts";
    } else {
      return "redirect:/users/login?error=true";
    }
  }
}