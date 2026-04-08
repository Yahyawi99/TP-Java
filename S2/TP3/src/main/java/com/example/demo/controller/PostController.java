package com.example.demo.controller;

import com.example.demo.model.Post;
import com.example.demo.model.Comment;
import com.example.demo.model.User;
import com.example.demo.model.Article;
import com.example.demo.model.VideoPost;
import com.example.demo.services.PostService;
import com.example.demo.services.ReactionService;
import com.example.demo.services.TagService;
import jakarta.servlet.http.HttpSession;

import java.util.*;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/posts")
public class PostController {

  private final PostService postService;
  private final TagService tagService;
  private final ReactionService reactionService;

  public PostController(PostService postService, TagService tagService, ReactionService reactionService) {
    this.postService = postService;
    this.tagService = tagService;
    this.reactionService = reactionService;
  }

  @GetMapping
  public String listPosts(@RequestParam(required = false) String tag, Model model, HttpSession session) {
    User currentUser = (User) session.getAttribute("user");
    List<Post> posts = ((tag != null && !tag.trim().isEmpty()) ? postService.findByTag(tag) : postService.findAll());
    model.addAttribute("posts", posts);
    model.addAttribute("currentUser", currentUser);
    model.addAttribute("currentTag", tag);
    return "posts/index";
  }

  @GetMapping("/new")
  public String showCreateForm(Model model, HttpSession session) {
    User currentUser = (User) session.getAttribute("user");
    model.addAttribute("currentUser", currentUser);
    model.addAttribute("tagString", "");
    return "posts/form";
  }

  @PostMapping
  public String savePost(@RequestParam String type,
      @RequestParam String title,
      @RequestParam(required = false) String content,
      @RequestParam(required = false) String url,
      @RequestParam(name = "tagString", required = false) String tagString,
      HttpSession session) {
    User currentUser = (User) session.getAttribute("user");
    if (currentUser == null) {
      return "redirect:/users/login";
    }
    Post post;
    if ("article".equals(type)) {
      Article article = new Article();
      article.setContent(content);
      post = article;
    } else if ("video".equals(type)) {
      VideoPost videoPost = new VideoPost();
      videoPost.setUrl(url);
      post = videoPost;
    } else {
      return "redirect:/posts/new";
    }
    post.setTitle(title);
    post.setAuthor(currentUser);
    post.getTags().clear();
    post.getTags().addAll(tagService.parseTags(tagString));
    postService.save(post);
    return "redirect:/posts";
  }

  @PostMapping("/{id}/delete")
  public String deletePost(@PathVariable Long id, HttpSession session, RedirectAttributes redirectAttributes) {
    User currentUser = (User) session.getAttribute("user");
    if (currentUser == null) {
      return "redirect:/users/login";
    }
    boolean deleted = postService.deleteIfOwner(id, currentUser);
    if (!deleted) {
      redirectAttributes.addFlashAttribute("error", "You are not authorized to delete this post.");
    }
    return "redirect:/posts";
  }

  @GetMapping("/{id}")
  public String showDetail(@PathVariable Long id, Model model, HttpSession session) {
    Post post = postService.findById(id);
    if (post == null) {
      return "redirect:/posts";
    }
    User currentUser = (User) session.getAttribute("user");
    model.addAttribute("post", post);
    model.addAttribute("currentUser", currentUser);
    model.addAttribute("newComment", new Comment());
    model.addAttribute("reactionCounts", reactionService.countByPost(post));
    return "posts/detail";
  }
}