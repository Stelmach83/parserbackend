package dev.stelmach.csvuploadbnackend.rest;

import dev.stelmach.csvuploadbnackend.model.PersonDTO;

import java.util.List;

public class ParsingResponse {

	private int correctEntries;
	private int entriesWithErrors;
	private List<PersonDTO> addedEntries;
	private List<PersonDTO> invalidEntries;

	public ParsingResponse() {
	}

	public ParsingResponse(int correctEntries, int entriesWithErrors, List<PersonDTO> addedEntries, List<PersonDTO> invalidEntries) {
		this.correctEntries = correctEntries;
		this.entriesWithErrors = entriesWithErrors;
		this.addedEntries = addedEntries;
		this.invalidEntries = invalidEntries;
	}

	public int getCorrectEntries() {
		return correctEntries;
	}

	public void setCorrectEntries(int correctEntries) {
		this.correctEntries = correctEntries;
	}

	public int getEntriesWithErrors() {
		return entriesWithErrors;
	}

	public void setEntriesWithErrors(int entriesWithErrors) {
		this.entriesWithErrors = entriesWithErrors;
	}

	public List<PersonDTO> getAddedEntries() {
		return addedEntries;
	}

	public void setAddedEntries(List<PersonDTO> addedEntries) {
		this.addedEntries = addedEntries;
	}

	public List<PersonDTO> getInvalidEntries() {
		return invalidEntries;
	}

	public void setInvalidEntries(List<PersonDTO> invalidEntries) {
		this.invalidEntries = invalidEntries;
	}
}
