package com.agent7799.springboot.security.firstsecurityapp.util;


import com.agent7799.springboot.security.firstsecurityapp.model.Person;
import com.agent7799.springboot.security.firstsecurityapp.services.PersonDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;



@Component
public class PersonValidator implements Validator {

    private final PersonDetailsService personDetailsService;

    @Autowired
    public PersonValidator(PersonDetailsService personDetailsService) {
        this.personDetailsService = personDetailsService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Person.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Person person = (Person) o;
        try {
            personDetailsService.loadUserByUsername(person.getUsername());
        } catch (UsernameNotFoundException e) {
            return; // all is ok, user not found
        }

        errors.rejectValue("username", "", "Person with such name already exists");

    }
}
