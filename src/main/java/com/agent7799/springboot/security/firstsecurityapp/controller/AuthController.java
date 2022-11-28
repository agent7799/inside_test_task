package com.agent7799.springboot.security.firstsecurityapp.controller;

import com.agent7799.springboot.security.firstsecurityapp.dto.AuthenticationDTO;
import com.agent7799.springboot.security.firstsecurityapp.dto.MessageDTO;
import com.agent7799.springboot.security.firstsecurityapp.dto.PersonDTO;
import com.agent7799.springboot.security.firstsecurityapp.model.Message;
import com.agent7799.springboot.security.firstsecurityapp.model.Person;
import com.agent7799.springboot.security.firstsecurityapp.security.JWTUtil;
import com.agent7799.springboot.security.firstsecurityapp.services.RegistrationService;
import com.agent7799.springboot.security.firstsecurityapp.util.PersonValidator;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final RegistrationService registrationService;
    private final PersonValidator personValidator;
    private final JWTUtil jwtUtil;
    private final ModelMapper modelMapper;
    private final AuthenticationManager authenticationManager;



    @Autowired
    public AuthController(RegistrationService registrationService, PersonValidator personValidator, JWTUtil jwtUtil, ModelMapper modelMapper, AuthenticationManager authenticationManager) {
        this.registrationService = registrationService;
        this.personValidator = personValidator;
        this.jwtUtil = jwtUtil;
        this.modelMapper = modelMapper;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/login")
    public Map<String, String> performLogin(@RequestBody AuthenticationDTO authenticationDTO) {
        UsernamePasswordAuthenticationToken authInputToken =
                new UsernamePasswordAuthenticationToken(authenticationDTO.getUsername(),
                        authenticationDTO.getPassword());
        try {
            authenticationManager.authenticate(authInputToken);
        } catch (BadCredentialsException e) {
            return Map.of("message", "WRONG LOGIN / PASSWORD");
        }
        String usernameFromAuthenticationDTO = authenticationDTO.getUsername();
        //System.out.println("usernameFromauthenticationDTO: " + usernameFromAuthenticationDTO);
        String token = jwtUtil.generateToken(usernameFromAuthenticationDTO);
        //System.out.println("login generated token: " + token);
        return Map.of("token", token);
    }



    @PostMapping("/registration")
    public Map<String, String> performRegistration(@RequestBody @Valid PersonDTO personDTO, BindingResult bindingResult) {
        log.info("performRegistration method");
        Person person = convertToPerson(personDTO);
        personValidator.validate(person, bindingResult);

        if (bindingResult.hasErrors()) {
            return Map.of("message", "REGISTRATION ERROR");
        }

        registrationService.register(person);
        String token = jwtUtil.generateToken(person.getUsername());
        return Map.of("status", "registration success", "token", token);
    }

    public Person convertToPerson(PersonDTO personDTO) {
        return this.modelMapper.map(personDTO, Person.class);
    }

    public Message convertToMessage(MessageDTO messageDTO) {
        return this.modelMapper.map(messageDTO, Message.class);
    }

}
