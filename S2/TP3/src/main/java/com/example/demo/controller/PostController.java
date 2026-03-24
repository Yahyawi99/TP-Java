package com.example.demo.controller;

import com.example.demo.model.Post;
import com.example.demo.model.Comment;
import com.example.demo.model.User;
import com.example.demo.services.PostService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/posts")
public class PostController {

  private final PostService postService;

  public PostController(PostService postService) {
    this.postService = postService;
  }

  // List all posts
  @GetMapping
  public String listPosts(Model model) {
    model.addAttribute("posts", postService.findAll());
    return "posts/index";
  }

  // Show form to create a new post
  @GetMapping("/new")
  public String showCreateForm(Model model) {
    model.addAttribute("post", new Post());
    return "posts/form";
  }

  // Save the post from the form
  @PostMapping
  public String savePost(@ModelAttribute Post post) {
    postService.save(post);
    return "redirect:/posts";
  }

  @GetMapping("/{id}")
  public String showDetail(@PathVariable Long id, Model model, HttpSession session) {
    Post post = postService.findById(id);
    User currentUser = (User) session.getAttribute("user");
    model.addAttribute("post", post);
    model.addAttribute("currentUser", currentUser);
    model.addAttribute("newComment", new Comment());
    return "posts/detail";
  }
}