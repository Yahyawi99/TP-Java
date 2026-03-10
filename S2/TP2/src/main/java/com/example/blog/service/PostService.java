package com.example.blog.service;

import com.example.blog.model.Post;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.ArrayList;

@Service
public class PostService {

  public List<Post> getAllPosts() {
    return new ArrayList<>();
  }

  public Post getPostById(Long id) {
    return new Post();
  }

  public Post createPost(Post post) {
    return post;
  }
}
