package com.example.tuesday.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = TitleValidator.class)
public @interface Title {

  String message() default
          "Title has to be between 2 and 100 characters in length and" +
                  " be composed of only letters.";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
