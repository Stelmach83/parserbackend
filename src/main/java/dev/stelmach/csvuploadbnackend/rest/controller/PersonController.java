package dev.stelmach.csvuploadbnackend.rest.controller;

import dev.stelmach.csvuploadbnackend.model.Person;
import dev.stelmach.csvuploadbnackend.service.PersonService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("${api.version}")
public class PersonController {

	private PersonService personService;

	public PersonController(PersonService personService) {
		this.personService = personService;
	}

	@GetMapping("/users")
	public ResponseEntity<List<Person>> getAll() {
		return new ResponseEntity<>(personService.getAllEntriesSortedByBirthday(), HttpStatus.OK);
	}

	@GetMapping("/users/page")
	public ResponseEntity<Page<Person>> getUserPage(@RequestParam("page") int page, @RequestParam(value = "size", defaultValue = "5") int size) {
		Pageable pageable = PageRequest.of(page, size);
		Page<Person> personPage = personService.getPaginatedEntries(pageable);
		return new ResponseEntity<>(personPage, HttpStatus.OK);
	}

}
