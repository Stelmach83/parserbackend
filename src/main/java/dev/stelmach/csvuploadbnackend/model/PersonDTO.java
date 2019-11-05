package dev.stelmach.csvuploadbnackend.model;

import dev.stelmach.csvuploadbnackend.validation.RequiredAndNotNull;
import dev.stelmach.csvuploadbnackend.validation.UniquePhoneNumber;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import java.util.Date;
import java.util.List;

public class PersonDTO {

	private Long id;
	@RequiredAndNotNull(message = "First name is required.")
	private String firstName;
	@RequiredAndNotNull(message = "Last name is required.")
	private String lastName;
	@NotNull(message = "Date of birth is required.")
	@Past(message = "Birthday must be a date in the past.")
	private Date dateOfBirth;
	@Pattern(regexp = "^[1-9][0-9]{8}$", message = "Phone number must be 9 digits long, and not start with '0'.")
	@UniquePhoneNumber(message = "An entry with this phone number already exists.")
	private String phoneNumber;
	private List<String> parsingMessages;

	public PersonDTO() {
	}

	public PersonDTO(String firstName, String lastName, Date dateOfBirth, String phoneNumber) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.dateOfBirth = dateOfBirth;
		this.phoneNumber = phoneNumber;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public List<String> getParsingMessages() {
		return parsingMessages;
	}

	public void setParsingMessages(List<String> parsingMessages) {
		this.parsingMessages = parsingMessages;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
	}
}
