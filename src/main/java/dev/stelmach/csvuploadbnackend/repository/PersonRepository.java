package dev.stelmach.csvuploadbnackend.repository;

import dev.stelmach.csvuploadbnackend.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

	long countPersonByPhoneNumber(String phoneNumber);

}
