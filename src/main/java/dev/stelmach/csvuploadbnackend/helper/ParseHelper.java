package dev.stelmach.csvuploadbnackend.helper;

import com.univocity.parsers.common.record.Record;
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

	public static Person convertPersonDTOtoPerson(PersonDTO personDTO) {
		Person person = new Person();
		person.setFirstName(personDTO.getFirstName());
		person.setLastName(personDTO.getLastName());
		person.setDateOfBirth(personDTO.getDateOfBirth());
		person.setPhoneNumber(personDTO.getPhoneNumber());
		return person;
	}

	public static PersonDTO convertRecordToPersonDTO(Record record) {
		String firstName = convertToTitleCase(record.getString("first_name"));
		String lastName = convertToTitleCase(record.getString("last_name"));
		Date dateOfBirth = ParseHelper.parseDate(record.getString("birth_date"));
		String phoneNumber = record.getString("phone_no");
		return new PersonDTO(firstName, lastName, dateOfBirth, phoneNumber);
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
		if (text == null || text.isEmpty() || text.equals("null")) {
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