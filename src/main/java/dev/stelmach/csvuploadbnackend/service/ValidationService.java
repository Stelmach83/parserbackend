package dev.stelmach.csvuploadbnackend.service;

import dev.stelmach.csvuploadbnackend.model.PersonDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
public class ValidationService {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

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
			if (log.isInfoEnabled()) {
				String logMsg = "A total of %d errors have been found in entry.";
				log.info(String.format(logMsg, violationMessages.size()));
			}
		}
		return violationMessages;
	}

}
