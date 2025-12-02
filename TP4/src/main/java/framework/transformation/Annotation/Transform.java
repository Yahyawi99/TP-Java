package main.java.framework.transformation.Annotation;

import java.lang.annotation.*;

/**
 * Annotation pour marquer les méthodes de transformation
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Transform {
  String description() default "";

  int order() default 0;
}