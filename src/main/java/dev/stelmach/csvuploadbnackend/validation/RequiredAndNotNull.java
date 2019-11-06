package dev.stelmach.csvuploadbnackend.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = RequiredAndNotNullValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface RequiredAndNotNull {

	String message() default "This field is required, and must not bu 'null'.";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};

}
