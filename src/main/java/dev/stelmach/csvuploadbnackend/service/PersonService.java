package dev.stelmach.csvuploadbnackend.service;

import dev.stelmach.csvuploadbnackend.model.Person;
import dev.stelmach.csvuploadbnackend.repository.PersonRepository;
import org.springframework.stereotype.Service;

@Service
public class PersonService {

	private PersonRepository personRepository;

	public PersonService(PersonRepository personRepository) {
		this.personRepository = personRepository;
	}

	public void savePerson(Person person) {
		personRepository.save(person);
	}

	public boolean isPhoneNumberUnique(String phoneNumber) {
		return personRepository.countPersonByPhoneNumber(phoneNumber) <= 0;
	}

}
