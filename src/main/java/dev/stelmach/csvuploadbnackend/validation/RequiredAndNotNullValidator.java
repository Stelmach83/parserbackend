package dev.stelmach.csvuploadbnackend.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class RequiredAndNotNullValidator implements ConstraintValidator<RequiredAndNotNull, String> {

	@Override
	public void initialize(RequiredAndNotNull constraintAnnotation) {
	}

	@Override
	public boolean isValid(String field, ConstraintValidatorContext constraintValidatorContext) {
		return field != null && !field.equals("null") && field.length() > 0;
	}

}

