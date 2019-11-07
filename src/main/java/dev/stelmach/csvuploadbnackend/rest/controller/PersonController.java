package dev.stelmach.csvuploadbnackend.rest.controller;

import dev.stelmach.csvuploadbnackend.model.Person;
import dev.stelmach.csvuploadbnackend.service.PersonService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("${api.version}")
public class PersonController {

	private static final String DOB = "dateOfBirth";

	private PersonService personService;

	public PersonController(PersonService personService) {
		this.personService = personService;
	}

	@GetMapping("/users")
	public ResponseEntity<List<Person>> getAllUsers() {
		List<Person> allEntriesSortedByBirthday = personService.getAllEntriesSortedByBirthday();
		return new ResponseEntity<>(allEntriesSortedByBirthday, HttpStatus.OK);
	}

	@GetMapping("/users/page")
	public ResponseEntity<Page<Person>> getUserPage(@RequestParam("page") int page, @RequestParam(value = "size", defaultValue = "5") int size) {
		Pageable pageable = PageRequest.of(page, size, Sort.by(DOB));
		Page<Person> personPage = personService.getPaginatedEntries(pageable);
		return new ResponseEntity<>(personPage, HttpStatus.OK);
	}

	@DeleteMapping("/users/delete")
	public ResponseEntity<Page<Person>> deleteUser(@RequestParam("page") int page, @RequestParam Long id, @RequestParam(value = "size", defaultValue = "5") int size) {
		Pageable pageable = PageRequest.of(page, size, Sort.by(DOB));
		Page<Person> personPage = personService.getPaginatedEntries(pageable);
		personService.deletePerson(id);
		// Below is the support for correctly returning the number of pages if after the deletion of an entry the page count changes.
		// Two queries are required for this.
		if (personPage.getTotalElements() % size == 1 && page == personPage.getTotalPages() - 1) {
			pageable = PageRequest.of(page, size, Sort.by(DOB));
		}
		personPage = personService.getPaginatedEntries(pageable);
		return new ResponseEntity<>(personPage, HttpStatus.OK);
	}

	@DeleteMapping("/users/deleteAll")
	public ResponseEntity<Page<Person>> deleteAllUsers(@RequestParam(value = "page", defaultValue = "0") int page, @RequestParam(value = "size", defaultValue = "5") int size) {
		personService.deleteAllPersons();
		Pageable pageable = PageRequest.of(page, size);
		Page<Person> personPage = personService.getPaginatedEntries(pageable);
		return new ResponseEntity<>(personPage, HttpStatus.OK);
	}

}
