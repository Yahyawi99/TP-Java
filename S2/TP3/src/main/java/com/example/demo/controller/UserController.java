package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.model.Profile;
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
    if ("admin@example.com".equals(user.getEmail())) {
      user.setRole("ADMIN");
    }
    Profile profile = new Profile();
    profile.setBio("Hello! I just joined the blog.");
    profile.setAvatar("/images/default-avatar.jpg");
    user.setProfile(profile);
    userService.save(user);
    return "redirect:/posts";
  }

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
      return "redirect:/posts";
    }
    model.addAttribute("currentUser", currentUser);
    model.addAttribute("users", userService.findAll());
    return "users/list";
  }

  @GetMapping("/{id}")
  public String showUserProfile(@PathVariable Long id, Model model, HttpSession session) {
    User profileUser = userService.findById(id);
    if (profileUser == null) {
      return "redirect:/posts";
    }

    User currentUser = (User) session.getAttribute("user");
    model.addAttribute("currentUser", currentUser);
    model.addAttribute("profileUser", profileUser);
    return "users/profile";
  }

  @PostMapping("/{id}/profile")
  public String updateProfile(@PathVariable Long id, @RequestParam String bio, @RequestParam String avatar,
      HttpSession session) {
    User currentUser = (User) session.getAttribute("user");
    if (currentUser == null || !currentUser.getId().equals(id)) {
      return "redirect:/posts";
    }

    User user = userService.findById(id);
    if (user != null && user.getProfile() != null) {
      user.getProfile().setBio(bio);
      user.getProfile().setAvatar(avatar);
      userService.save(user);
    }
    return "redirect:/users/" + id;
  }
}