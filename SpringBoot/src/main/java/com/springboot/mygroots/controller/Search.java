package com.springboot.mygroots.controller;

import com.springboot.mygroots.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.mygroots.model.Person;

@RestController
@RequestMapping(value="/search")
public class Search {

    @Autowired
    PersonService personService;

    @RequestMapping(value= "/id")
    public Person searchById(@RequestBody String id) {
        return personService.getPersonById(id);
    }

    @RequestMapping(value= "/name")
    public Person searchByName(@RequestBody String name, @RequestBody String lastName) {
        return personService.getPersonByNameAndLastName(name, lastName);
    }
}
