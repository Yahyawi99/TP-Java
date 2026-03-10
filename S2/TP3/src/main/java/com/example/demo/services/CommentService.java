package com.example.demo.services;

import com.example.demo.model.Comment;
import java.util.List;
import com.example.demo.dao.repositories.CommentRepository;
import org.springframework.stereotype.Service;

@Service
public class CommentService {
  private final CommentRepository commentRepository;

  public CommentService(CommentRepository commentRepository) {
    this.commentRepository = commentRepository;
  }

  public List<Comment> findAll() {
    return commentRepository.findAll();
  }

  public Comment findById(Long id) {
    return commentRepository.findById(id).orElse(null);
  }

  public Comment save(Comment comment) {
    return commentRepository.save(comment);
  }

  public void delete(Long id) {
    commentRepository.deleteById(id);
  }
}