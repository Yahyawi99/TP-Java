package com.example.demo.controller;

import com.example.demo.model.Post;
import com.example.demo.services.PostService;
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
    return "posts/list";
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
}