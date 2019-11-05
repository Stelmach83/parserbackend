package dev.stelmach.csvuploadbnackend.helper;

import com.univocity.parsers.common.record.Record;
import dev.stelmach.csvuploadbnackend.model.PersonDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Objects;

public class ParseHelper {

	private ParseHelper() {
	}

	public static PersonDTO convertRecordToPerson(Record record) {
		String firstName = convertToTitleCase(record.getString("first_name"));
		String lastName = convertToTitleCase(record.getString("last_name"));
		Date dateOfBirth = ParseHelper.parseDate(record.getString("birth_date"));
		String phoneNumber = record.getString("phone_no");
		List<String> parsingMessages = new ArrayList<>();
		if (validatePersonRecord(record)) {
			parsingMessages.add("Ok");
		} else {
			parsingMessages.add("Error 1");
			parsingMessages.add("Error 2");
		}
		return new PersonDTO(firstName, lastName, dateOfBirth, phoneNumber, parsingMessages);
	}

	public static boolean validatePersonRecord(Record record) {
		String firstName = record.getString("first_name");
		String lastName = record.getString("last_name");
		Date dateOfBirth = ParseHelper.parseDate(record.getString("birth_date"));
		String phoneNumber = record.getString("phone_no");
		return firstName != null && lastName != null && dateOfBirth != null && phoneNumber != null;
	}

	public static File convertMultipartFileToFile(MultipartFile file) {
		File javaFile = new File(Objects.requireNonNull(file.getOriginalFilename()));
		try {
			boolean newFile = javaFile.createNewFile();
			if (newFile) {
				FileOutputStream fos = new FileOutputStream(javaFile);
				fos.write(file.getBytes());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return javaFile;
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

	private static boolean isNumeric(String str) {
		try {
			double d = Double.parseDouble(str);
			return true;
		} catch (Exception e) {
		}
		return false;
	}

}
