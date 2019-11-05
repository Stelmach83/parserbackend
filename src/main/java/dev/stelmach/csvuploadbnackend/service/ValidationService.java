package dev.stelmach.csvuploadbnackend.service;

import dev.stelmach.csvuploadbnackend.model.PersonDTO;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
public class ValidationService {

	private Validator validator;

	public ValidationService(Validator validator) {
		this.validator = validator;
	}

	public List<String> validatePersonDTO(PersonDTO personDTO) {
		Set<ConstraintViolation<PersonDTO>> violations = validator.validate(personDTO);
		List<String> violationMessages = new ArrayList<>();
		if (!violations.isEmpty()) {
			for (ConstraintViolation<PersonDTO> violation : violations) {
				violationMessages.add(violation.getMessage());
			}
		}
		return violationMessages;
	}

}
