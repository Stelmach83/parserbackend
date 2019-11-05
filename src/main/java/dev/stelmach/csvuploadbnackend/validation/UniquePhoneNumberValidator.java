package dev.stelmach.csvuploadbnackend.validation;

import dev.stelmach.csvuploadbnackend.service.PersonService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniquePhoneNumberValidator implements ConstraintValidator<UniquePhoneNumber, String> {

	private PersonService personService;

	public UniquePhoneNumberValidator(PersonService personService) {
		this.personService = personService;
	}

	public void initialize(UniquePhoneNumber constraintAnnotation) {
	}

	@Override
	public boolean isValid(String phoneNumber, ConstraintValidatorContext constraintValidatorContext) {
		return personService.isPhoneNumberUnique(phoneNumber);
	}
}
