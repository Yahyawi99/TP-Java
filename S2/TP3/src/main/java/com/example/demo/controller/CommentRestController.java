package com.example.demo.controller;

import com.example.demo.model.Comment;
import com.example.demo.model.Post;
import com.example.demo.services.CommentService;
import com.example.demo.services.PostService;
import org.springframework.web.bind.annotation.*;

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
  public Comment addComment(@RequestBody Comment comment) {
    return commentService.save(comment);
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