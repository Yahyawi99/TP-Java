package com.example.demo.services;

import com.example.demo.dao.repositories.ReactionRepository;
import com.example.demo.model.Post;
import com.example.demo.model.Reaction;
import com.example.demo.model.ReactionType;
import com.example.demo.model.User;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ReactionService {
  private final ReactionRepository reactionRepository;

  public ReactionService(ReactionRepository reactionRepository) {
    this.reactionRepository = reactionRepository;
  }

  public Reaction save(Reaction reaction) {
    return reactionRepository.save(reaction);
  }

  public Reaction addOrUpdateReaction(Post post, User user, ReactionType type) {
    Optional<Reaction> existing = reactionRepository.findByPostAndUser(post, user);
    if (existing.isPresent()) {
      Reaction reaction = existing.get();
      if (reaction.getType() != type) {
        reaction.setType(type);
        reaction.setCreatedAt(LocalDateTime.now());
      }
      return reactionRepository.save(reaction);
    }
    Reaction reaction = new Reaction();
    reaction.setPost(post);
    reaction.setUser(user);
    reaction.setType(type);
    return reactionRepository.save(reaction);
  }

  public List<Reaction> findByPost(Post post) {
    return reactionRepository.findByPost(post);
  }

  public Map<ReactionType, Long> countByPost(Post post) {
    Map<ReactionType, Long> counts = new EnumMap<>(ReactionType.class);
    for (ReactionType type : ReactionType.values()) {
      counts.put(type, reactionRepository.countByPostAndType(post, type));
    }
    return counts;
  }
}