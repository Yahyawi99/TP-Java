package com.example.demo.dao.repositories;

import com.example.demo.model.Post;
import com.example.demo.model.Reaction;
import com.example.demo.model.ReactionType;
import com.example.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReactionRepository extends JpaRepository<Reaction, Long> {
  List<Reaction> findByPost(Post post);

  Optional<Reaction> findByPostAndUser(Post post, User user);

  long countByPostAndType(Post post, ReactionType type);
}