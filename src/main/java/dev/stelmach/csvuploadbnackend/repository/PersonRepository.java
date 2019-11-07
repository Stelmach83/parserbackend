package dev.stelmach.csvuploadbnackend.repository;

import dev.stelmach.csvuploadbnackend.model.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

	long countPersonByPhoneNumber(String phoneNumber);
	List<Person> findAllByOrderByDateOfBirth();
	Page<Person> findAllByLastNameIgnoreCaseContaining(Pageable pageable, String lastName);

}
