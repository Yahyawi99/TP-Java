package com.example.demo.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "post")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "post_type", discriminatorType = DiscriminatorType.STRING)
public abstract class Post {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String title;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User author;

  @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<Comment> comments = new ArrayList<>();

  @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
  private List<Reaction> reactions = new ArrayList<>();

  @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
  @JoinTable(name = "post_tags", joinColumns = @JoinColumn(name = "post_id"), inverseJoinColumns = @JoinColumn(name = "tag_id"))
  private java.util.Set<Tag> tags = new java.util.HashSet<>();

  public Post() {
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public User getAuthor() {
    return author;
  }

  public void setAuthor(User author) {
    this.author = author;
  }

  public List<Comment> getComments() {
    return comments;
  }

  public List<Reaction> getReactions() {
    return reactions;
  }

  public void setReactions(List<Reaction> reactions) {
    this.reactions = reactions;
  }

  public java.util.Set<Tag> getTags() {
    return tags;
  }

  public void setTags(java.util.Set<Tag> tags) {
    this.tags = tags;
  }

  public String getContent() {
    return null;
  }

  public String getUrl() {
    return null;
  }

  public void addReaction(Reaction reaction) {
    this.reactions.add(reaction);
    reaction.setPost(this);
  }

  public void removeReaction(Reaction reaction) {
    this.reactions.remove(reaction);
    reaction.setPost(null);
  }

  public void addTag(Tag tag) {
    this.tags.add(tag);
    if (tag.getPosts() == null) {
      tag.setPosts(new java.util.HashSet<>());
    }
    tag.getPosts().add(this);
  }

  public void removeTag(Tag tag) {
    if (this.tags != null) {
      this.tags.remove(tag);
    }
    if (tag.getPosts() != null) {
      tag.getPosts().remove(this);
    }
  }
}