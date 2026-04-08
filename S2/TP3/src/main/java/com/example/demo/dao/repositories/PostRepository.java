package com.example.demo.dao.repositories;

import com.example.demo.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

  @Query("SELECT p FROM Post p JOIN p.tags t WHERE t.name = :tagName")
  List<Post> findByTagName(@Param("tagName") String tagName);
}