package com.project.SpringBatch.Repository;

import com.project.SpringBatch.Entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {
}
