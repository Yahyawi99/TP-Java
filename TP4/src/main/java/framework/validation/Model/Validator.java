package main.java.framework.validation.Model;

@FunctionalInterface
public interface Validator<T> {
  ValidationResult<T> validate(T object);

  /**
   * Compose deux validateurs
   */
  default Validator<T> and(Validator<T> other) {
    return obj -> {
      ValidationResult<T> first = this.validate(obj);
      if (!first.isValid()) {
        return first;
      }
      return other.validate(obj);
    };
  }
}