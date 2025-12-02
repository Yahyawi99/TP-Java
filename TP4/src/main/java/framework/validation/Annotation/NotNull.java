package main.java.framework.validation.Annotation;

import java.lang.annotation.*;

/**
 * Annotation pour valider qu'un champ n'est pas null
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface NotNull {
  String message() default "Le champ ne peut pas être null";
}