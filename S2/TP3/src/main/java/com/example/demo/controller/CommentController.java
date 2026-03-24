package com.example.demo.controller;

import com.example.demo.model.Comment;
import com.example.demo.model.Post;
import com.example.demo.model.User;
import com.example.demo.services.CommentService;
import com.example.demo.services.PostService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/comments")
public class CommentController {

  private final CommentService commentService;
  private final PostService postService;

  public CommentController(CommentService commentService, PostService postService) {
    this.commentService = commentService;
    this.postService = postService;
  }

  // List comments for a specific post
  @GetMapping("/post/{postId}")
  public String listComments(@PathVariable Long postId, Model model) {
    Post post = postService.findById(postId);
    if (post == null) {
      return "redirect:/posts";
    }
    model.addAttribute("post", post);
    model.addAttribute("comments", post.getComments());
    return "comments/index";
  }

  // Show form to create a new comment
  @GetMapping("/post/{postId}/new")
  public String showCreateForm(@PathVariable Long postId, Model model, HttpSession session) {
    User user = (User) session.getAttribute("user");
    if (user == null) {
      return "redirect:/users/login";
    }
    Post post = postService.findById(postId);
    if (post == null) {
      return "redirect:/posts";
    }
    Comment comment = new Comment();
    comment.setPost(post);
    model.addAttribute("comment", comment);
    model.addAttribute("post", post);
    return "comments/form";
  }

  // Save the comment from the form
  @PostMapping
  public String saveComment(@RequestParam Long postId, @RequestParam String content, HttpSession session) {
    User user = (User) session.getAttribute("user");
    if (user == null) {
      return "redirect:/users/login";
    }
    Post post = postService.findById(postId);
    if (post == null) {
      return "redirect:/posts";
    }
    Comment comment = new Comment();
    comment.setPost(post);
    comment.setAuthor(user);
    comment.setContent(content);
    commentService.save(comment);
    return "redirect:/posts/" + postId;
  }

  // Show form to edit a comment
  @GetMapping("/{id}/edit")
  public String showEditForm(@PathVariable Long id, Model model, HttpSession session) {
    User user = (User) session.getAttribute("user");
    Comment comment = commentService.findById(id);
    if (comment == null || user == null || !comment.getAuthor().getId().equals(user.getId())) {
      return "redirect:/posts";
    }
    model.addAttribute("comment", comment);
    return "comments/form";
  }

  // Update the comment
  @PostMapping("/{id}")
  public String updateComment(@PathVariable Long id, @ModelAttribute Comment comment, HttpSession session) {
    User user = (User) session.getAttribute("user");
    Comment existingComment = commentService.findById(id);
    if (existingComment == null || user == null || !existingComment.getAuthor().getId().equals(user.getId())) {
      return "redirect:/posts";
    }
    existingComment.setContent(comment.getContent());
    commentService.save(existingComment);
    return "redirect:/comments/post/" + existingComment.getPost().getId();
  }

  // Delete a comment
  @PostMapping("/{id}/delete")
  public String deleteComment(@PathVariable Long id, HttpSession session) {
    User user = (User) session.getAttribute("user");
    Comment comment = commentService.findById(id);
    if (comment == null || user == null || !comment.getAuthor().getId().equals(user.getId())) {
      return "redirect:/posts";
    }
    Long postId = comment.getPost().getId();
    commentService.delete(id);
    return "redirect:/comments/post/" + postId;
  }
}