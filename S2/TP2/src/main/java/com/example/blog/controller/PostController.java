package com.example.blog.controller;

import com.example.blog.model.Post;
import com.example.blog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class PostController {
  private final PostService postService;

  @Autowired
  public PostController(PostService postService) {
    this.postService = postService;
  }

  @GetMapping("/posts")
  public String listPosts(Model model) {
    model.addAttribute("posts", postService.getAllPosts());
    return "posts/list";
  }

  @GetMapping("/posts/{id}")
  public String viewPost(@PathVariable Long id, Model model) {
    Post post = postService.getPostById(id);
    model.addAttribute("post", post);
    return "posts/view";
  }

  @PostMapping("/posts")
  public String createPost(Post post) {
    postService.createPost(post);
    return "redirect:/posts";
  }
}