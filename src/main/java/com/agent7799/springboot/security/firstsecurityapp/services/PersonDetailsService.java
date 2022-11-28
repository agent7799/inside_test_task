package com.agent7799.springboot.security.firstsecurityapp.services;

import com.agent7799.springboot.security.firstsecurityapp.model.Person;
import com.agent7799.springboot.security.firstsecurityapp.repositories.PeopleRepository;
import com.agent7799.springboot.security.firstsecurityapp.security.PersonDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PersonDetailsService implements UserDetailsService {

    private final PeopleRepository peopleRepository;


    @Autowired
    public PersonDetailsService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Person> person = peopleRepository.findByUsername(username);
        if (person.isEmpty()){
            throw  new UsernameNotFoundException("User Not Found!");
        }
        return new PersonDetails(person.get());
    }
}
