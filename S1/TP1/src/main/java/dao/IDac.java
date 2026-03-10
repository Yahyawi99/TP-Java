package dao;

import java.util.List;

public interface IDac<T> {
  boolean create(T data);

  boolean update(T data);

  boolean delete(T data);

  T findById(int id);

  List<T> findAll();

}
