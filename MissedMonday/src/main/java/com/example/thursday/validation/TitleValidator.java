package com.example.thursday.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class TitleValidator implements ConstraintValidator<Title, String> {


  @Override
  public boolean isValid(String title, ConstraintValidatorContext constraintValidatorContext) {
    return title != null &&
            title.length() > 2 &&
            title.length() <= 100 &&
            title.matches("[a-zA-Z]+");
  }
}
