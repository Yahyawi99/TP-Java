package com.example.demo.services;

import com.example.demo.model.Post;
import java.util.List;
import com.example.demo.dao.repositories.PostRepository;
import org.springframework.stereotype.Service;

@Service
public class PostService {
  private final PostRepository postRepository;

  public PostService(PostRepository postRepository) {
    this.postRepository = postRepository;
  }

  public List<Post> findAll() {
    return postRepository.findAll();
  }

  public Post findById(Long id) {
    return postRepository.findById(id).orElse(null);
  }

  public Post save(Post post) {
    return postRepository.save(post);
  }

  public void delete(Long id) {
    postRepository.deleteById(id);
  }
}