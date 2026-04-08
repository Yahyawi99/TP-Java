package com.example.demo.controller;

import com.example.demo.model.Post;
import com.example.demo.model.Reaction;
import com.example.demo.model.ReactionType;
import com.example.demo.model.User;
import com.example.demo.services.PostService;
import com.example.demo.services.ReactionService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/reactions")
public class ReactionController {

  private final ReactionService reactionService;
  private final PostService postService;

  public ReactionController(ReactionService reactionService, PostService postService) {
    this.reactionService = reactionService;
    this.postService = postService;
  }

  @PostMapping("/post/{postId}")
  public String addReaction(@PathVariable Long postId, @RequestParam ReactionType type, HttpSession session) {
    User user = (User) session.getAttribute("user");
    if (user == null) {
      return "redirect:/users/login";
    }
    Post post = postService.findById(postId);
    if (post == null) {
      return "redirect:/posts";
    }
    reactionService.addOrUpdateReaction(post, user, type);
    return "redirect:/posts/" + postId;
  }
}
