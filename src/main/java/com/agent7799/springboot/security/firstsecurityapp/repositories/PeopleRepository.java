package com.agent7799.springboot.security.firstsecurityapp.repositories;

import com.agent7799.springboot.security.firstsecurityapp.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PeopleRepository extends JpaRepository<Person, Integer> {

    Optional<Person> findByUsername(String username);


}
