package com.springboot.mygroots.controller;

import com.springboot.mygroots.service.PersonService;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.mygroots.model.Person;

@RestController
@RequestMapping(value="/search")
@CrossOrigin(origins = "http://localhost:4200")
public class Search {

    @Autowired
    PersonService personService;

    @GetMapping(value= "/id")
    public Person searchById(@RequestBody String id) {
        return personService.getPersonById(id);
    }

    @GetMapping(value= "/name")
    public Person searchByName(@RequestBody String firstName, @RequestBody String lastName, @RequestBody LocalDate birthdate) {
        return personService.getPersonByFirstNameAndLastNameAndBirthDate(firstName, lastName, birthdate);
    }
}
