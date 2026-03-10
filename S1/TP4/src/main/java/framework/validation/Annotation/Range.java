package main.java.framework.validation.Annotation;

import java.lang.annotation.*;

/**
 * Annotation pour valider une plage de valeurs
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Range {
  double min() default Double.MIN_VALUE;

  double max() default Double.MAX_VALUE;

  String message() default "La valeur est hors de la plage autorisée";
}