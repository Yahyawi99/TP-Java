package netatlas.model;

import java.util.List;

public interface IDAO<T> {
  boolean create(T data);

  boolean update(T data);

  boolean delete(Long id);

  T findById(Long id);

  List<T> findAll();

}
