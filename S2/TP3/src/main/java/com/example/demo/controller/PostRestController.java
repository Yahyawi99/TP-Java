package com.example.demo.controller;

import com.example.demo.model.Post;
import com.example.demo.services.PostService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostRestController {

  private final PostService postService;

  public PostRestController(PostService postService) {
    this.postService = postService;
  }

  @GetMapping
  public List<Post> getAllPosts() {
    return postService.findAll();
  }

  @GetMapping("/{id}")
  public Post getPostById(@PathVariable Long id) {
    return postService.findById(id);
  }

  @PostMapping
  public Post createPost(@RequestBody Post post) {
    return postService.save(post);
  }

  @DeleteMapping("/{id}")
  public void deletePost(@PathVariable Long id) {
    postService.delete(id);
  }
}