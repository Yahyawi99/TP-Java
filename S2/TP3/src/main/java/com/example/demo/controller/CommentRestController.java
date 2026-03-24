package com.example.demo.controller;

import com.example.demo.model.Comment;
import com.example.demo.model.Post;
import com.example.demo.model.User;
import com.example.demo.services.CommentService;
import com.example.demo.services.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentRestController {

  private final CommentService commentService;
  private final PostService postService;

  public CommentRestController(CommentService commentService, PostService postService) {
    this.commentService = commentService;
    this.postService = postService;
  }

  @PostMapping
  public ResponseEntity<Comment> addComment(@RequestBody Comment comment, HttpSession session) {
    User user = (User) session.getAttribute("user");
    if (user == null) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
    comment.setAuthor(user);
    Comment savedComment = commentService.save(comment);
    return ResponseEntity.ok(savedComment);
  }

  @GetMapping("/post/{postId}")
  public List<Comment> getCommentsByPost(@PathVariable Long postId) {
    Post post = postService.findById(postId);
    return (post != null) ? post.getComments() : null;
  }

  @DeleteMapping("/{id}")
  public void deleteComment(@PathVariable Long id) {
    commentService.delete(id);
  }
}