package main.java.library.model;

import java.util.List;

public interface IDAO<T> {
  T findById(Long id);

  List<T> getAll();

  boolean create(T entity);

  boolean update(T entity);

  boolean delete(Long id);
}