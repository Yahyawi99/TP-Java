package com.example.demo.controller;

import com.example.demo.model.Post;
import com.example.demo.model.Comment;
import com.example.demo.dao.repositories.CommentRepository;
import com.example.demo.services.PostService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/posts")
public class PostController {

  private final PostService postService;
  private CommentRepository commentRepository;

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

  @GetMapping("/{id}")
  public String showDetail(@PathVariable Long id, Model model) {
    Post post = postService.findById(id);
    model.addAttribute("post", post);
    return "posts/detail";
  }

  // Quick Comment logic
  @PostMapping("/{id}/comments")
  public String addComment(@PathVariable Long id, @RequestParam String commentContent) {
    Post post = postService.findById(id);
    Comment comment = new Comment();
    comment.setContent(commentContent);
    comment.setPost(post);
    // Assuming you have a CommentService/Repo:
    commentRepository.save(comment);
    return "redirect:/posts/" + id;
  }
}