package dev.stelmach.csvuploadbnackend.helper;

import com.univocity.parsers.common.record.Record;
import dev.stelmach.csvuploadbnackend.exception.InvalidEntryException;
import dev.stelmach.csvuploadbnackend.model.Person;
import dev.stelmach.csvuploadbnackend.model.PersonDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.GregorianCalendar;

@Component
public class ParseHelper {

	private static final Logger log = LoggerFactory.getLogger(ParseHelper.class);

	private ParseHelper() {
	}

	public static PersonDTO convertPersonToPersonDTO(Person person) {
		PersonDTO personDTO = new PersonDTO();
		if (person.getId() != null) {
			personDTO.setId(person.getId());
		}
		personDTO.setFirstName(person.getFirstName());
		personDTO.setLastName(person.getLastName());
		personDTO.setDateOfBirth(person.getDateOfBirth());
		personDTO.setPhoneNumber(person.getPhoneNumber());
		if (log.isDebugEnabled()) {
			String logMsg = "Converted Person to PersonDTO: %s";
			log.debug(String.format(logMsg,  personDTO.toString()));
		}
		return personDTO;
	}

	public static Person convertPersonDTOtoPerson(PersonDTO personDTO) {
		Person person = new Person();
		if (personDTO.getId() != null) {
			person.setId(personDTO.getId());
		}
		person.setFirstName(personDTO.getFirstName());
		person.setLastName(personDTO.getLastName());
		person.setDateOfBirth(personDTO.getDateOfBirth());
		person.setPhoneNumber(personDTO.getPhoneNumber());
		if (log.isDebugEnabled()) {
			String logMsg = "Converted PersonDTO to Person: %s";
			log.debug(String.format(logMsg,  person.toString()));
		}
		return person;
	}

	public static PersonDTO convertRecordToPersonDTO(Record record) {
		String firstNameColumn;
		String lastNameColumn;
		String birthDateColumn;
		String phoneNumberColumn;
		try {
			firstNameColumn = record.getString("first_name");
			lastNameColumn = record.getString("last_name");
			birthDateColumn = record.getString("birth_date");
			phoneNumberColumn = record.getString("phone_no");
		} catch (Exception e) {
			throw new InvalidEntryException("Invalid CSV format. Required columns are: first_name;last_name;birth_date;phone_no");
		}
		String firstName = convertToTitleCase(firstNameColumn);
		String lastName = convertToTitleCase(lastNameColumn);
		Date dateOfBirth = ParseHelper.parseDate(birthDateColumn);
		PersonDTO personDTO = new PersonDTO(firstName, lastName, dateOfBirth, phoneNumberColumn);
		if (log.isDebugEnabled()) {
			String logMsg = "Converted Record to PersonDTO: %s";
			log.debug(String.format(logMsg, personDTO.toString()));
		}
		return personDTO;
	}

	private static Date parseDate(String dateString) {
		String[] dateElements = dateString.split("\\.");
		if (dateElements.length == 3) {
			int year = Integer.parseInt(dateElements[0]);
			int month = Integer.parseInt(dateElements[1]) - 1;
			int day = Integer.parseInt(dateElements[2]);
			return new GregorianCalendar(year, month, day).getTime();
		} else {
			return null;
		}
	}

	private static String convertToTitleCase(String text) {
		if (text == null || text.isEmpty() || text.equals("null") || text.equals("empty")) {
			return text;
		}
		StringBuilder converted = new StringBuilder();
		boolean convertNext = true;
		for (char ch : text.toCharArray()) {
			if (Character.isSpaceChar(ch)) {
				convertNext = true;
			} else if (convertNext) {
				ch = Character.toTitleCase(ch);
				convertNext = false;
			} else {
				ch = Character.toLowerCase(ch);
			}
			converted.append(ch);
		}
		return converted.toString();
	}

}
