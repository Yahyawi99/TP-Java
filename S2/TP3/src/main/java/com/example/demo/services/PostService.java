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

  public boolean deleteIfOwner(Long id, com.example.demo.model.User currentUser) {
    if (currentUser == null) {
      return false;
    }
    Post post = findById(id);
    if (post == null || post.getAuthor() == null || post.getAuthor().getId() == null) {
      return false;
    }
    if (!post.getAuthor().getId().equals(currentUser.getId())) {
      return false;
    }
    postRepository.delete(post);
    return true;
  }

  public List<Post> findByTag(String tagName) {
    return postRepository.findByTagName(tagName);
  }
}