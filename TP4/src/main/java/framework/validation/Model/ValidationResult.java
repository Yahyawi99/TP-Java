package main.java.framework.validation.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Classe générique pour encapsuler le résultat d'une validation
 * 
 * @param <T> Le type de l'objet validé
 */
public class ValidationResult<T> {
  private final T object;
  private final List<String> errors;
  private final boolean valid;

  private ValidationResult(T object, List<String> errors) {
    this.object = object;
    this.errors = new ArrayList<>(errors);
    this.valid = errors.isEmpty();
  }

  public static <T> ValidationResult<T> success(T object) {
    return new ValidationResult<>(object, List.of());
  }

  public static <T> ValidationResult<T> failure(T object, List<String> errors) {
    return new ValidationResult<>(object, errors);
  }

  public boolean isValid() {
    return valid;
  }

  public Optional<T> getObject() {
    return valid ? Optional.of(object) : Optional.empty();
  }

  public List<String> getErrors() {
    return new ArrayList<>(errors);
  }

  public <R> ValidationResult<R> map(java.util.function.Function<T, R> mapper) {
    if (valid) {
      R newObj = mapper.apply(object);
      return ValidationResult.success(newObj);
    } else {
      return ValidationResult.failure(null, errors);

    }
  }
}