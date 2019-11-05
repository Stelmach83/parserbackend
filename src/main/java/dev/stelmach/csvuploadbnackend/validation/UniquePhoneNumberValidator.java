//package dev.stelmach.csvuploadbnackend.validation;
//
//import dev.stelmach.csvuploadbnackend.service.PersonService;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import javax.validation.ConstraintValidator;
//import javax.validation.ConstraintValidatorContext;
//
//public class UniquePhoneNumberValidator implements ConstraintValidator<UniquePhoneNumber, String> {
//
//	@Autowired
//	private PersonService personService;
//
//	public void initialize(UniquePhoneNumber constraintAnnotation) {
//	}
//
//	@Override
//	public boolean isValid(String phoneNumber, ConstraintValidatorContext constraintValidatorContext) {
//		return personService.isPhoneNumberUnique(phoneNumber);
//	}
//}
