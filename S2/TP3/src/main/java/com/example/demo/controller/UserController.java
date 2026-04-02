package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;

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
    // For demo purposes, if email is admin@example.com, make admin
    if ("admin@example.com".equals(user.getEmail())) {
      user.setRole("ADMIN");
    }
    userService.save(user);
    return "redirect:/posts";
  }

  // For now, just redirect to posts after login
  @PostMapping("/login")
  public String login(@RequestParam String email, @RequestParam String password, HttpSession session) {
    if (userService.authenticate(email, password)) {
      User user = userService.findAll().stream()
          .filter(u -> u.getEmail().equals(email))
          .findFirst()
          .orElse(null);
      if (user != null) {
        session.setAttribute("user", user);
        return "redirect:/posts";
      }
    }
    return "redirect:/users/login?error=true";
  }

  @PostMapping("/logout")
  public String logout(HttpSession session) {
    session.invalidate();
    return "redirect:/users/login";
  }

  @GetMapping
  public String listUsers(Model model, HttpSession session) {
    User currentUser = (User) session.getAttribute("user");
    if (currentUser == null || !"ADMIN".equals(currentUser.getRole())) {
      return "redirect:/posts"; // Redirect if not admin
    }
    model.addAttribute("users", userService.findAll());
    return "users/list";
  }
}