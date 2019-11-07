package dev.stelmach.csvuploadbnackend.service;

import dev.stelmach.csvuploadbnackend.model.Person;
import dev.stelmach.csvuploadbnackend.repository.PersonRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonService {

	private PersonRepository personRepository;

	public PersonService(PersonRepository personRepository) {
		this.personRepository = personRepository;
	}

	public void savePerson(Person person) {
		personRepository.save(person);
	}

	public void deletePerson(Long id) {
		personRepository.deleteById(id);
	}

	public void deleteAllPersons() {
		personRepository.deleteAll();
	}

	public boolean isPhoneNumberUnique(String phoneNumber) {
		return personRepository.countPersonByPhoneNumber(phoneNumber) <= 0;
	}

	public List<Person> getAllEntriesSortedByBirthday() {
		return personRepository.findAllByOrderByDateOfBirthDesc();
	}

	public Page<Person> getPaginatedEntries(Pageable pageable) {
		return personRepository.findAll(pageable);
	}

}
