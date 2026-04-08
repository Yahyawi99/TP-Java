package com.example.demo.services;

import com.example.demo.dao.repositories.TagRepository;
import com.example.demo.model.Tag;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TagService {

  private final TagRepository tagRepository;

  public TagService(TagRepository tagRepository) {
    this.tagRepository = tagRepository;
  }

  public Set<Tag> parseTags(String tagString) {
    if (tagString == null || tagString.trim().isEmpty()) {
      return new HashSet<>();
    }
    return java.util.Arrays.stream(tagString.split(","))
        .map(String::trim)
        .filter(s -> !s.isEmpty())
        .map(this::findOrCreateTag)
        .collect(Collectors.toSet());
  }

  public Tag findOrCreateTag(String name) {
    return tagRepository.findByName(name)
        .orElseGet(() -> tagRepository.save(new Tag(name)));
  }
}