package main.java.framework.validation.Annotation;

import java.lang.annotation.*;

/**
 * Annotation pour valider une longueur minimale
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface MinLength {
  int value();

  String message() default "La longueur minimale n'est pas respectée";

}