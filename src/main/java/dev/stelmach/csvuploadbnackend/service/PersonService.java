package dev.stelmach.csvuploadbnackend.service;

import dev.stelmach.csvuploadbnackend.model.Person;
import dev.stelmach.csvuploadbnackend.repository.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonService {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	private PersonRepository personRepository;

	public PersonService(PersonRepository personRepository) {
		this.personRepository = personRepository;
	}

	public void savePerson(Person person) {
		personRepository.save(person);
	}

	public void deletePerson(Long id) {
		if (log.isDebugEnabled()) {
			String logMsg = "Attempting to delete Person with 'id' of: %s";
			log.debug(logMsg, id);
		}
		personRepository.deleteById(id);
	}

	public void deleteAllPersons() {
		if (log.isDebugEnabled()) {
			log.debug("Deleted all entries.");
		}
		personRepository.deleteAll();
	}

	public boolean isPhoneNumberUnique(String phoneNumber) {
		return personRepository.countPersonByPhoneNumber(phoneNumber) <= 0;
	}

	public List<Person> getAllEntriesSortedByBirthday() {
		List<Person> allByOrderByDateOfBirth = personRepository.findAllByOrderByDateOfBirth();
		if (log.isDebugEnabled()) {
			String logMsg = "Found, and returned all entries sorted by birthday. A total of %s entries.";
			log.debug(logMsg, allByOrderByDateOfBirth.size());
		}
		return allByOrderByDateOfBirth;
	}

	public Page<Person> getPaginatedEntries(Pageable pageable) {
		Page<Person> personPage = personRepository.findAll(pageable);
		if (log.isDebugEnabled()) {
			String logMsg = "Returned a paginated result of entries. Page: %s of %s entries.";
			log.debug(logMsg, pageable.getPageNumber(), personPage.getContent().size());
		}
		return personRepository.findAll(pageable);
	}

}
