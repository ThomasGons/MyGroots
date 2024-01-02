package com.springboot.mygroots.controller;

import com.springboot.mygroots.service.PersonService;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.springboot.mygroots.utils.ExtResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.mygroots.model.Person;

/**
 * Controller for the search
 */
@RestController
@RequestMapping(value="/search")
@CrossOrigin(origins = "http://localhost:4200")
public class SearchCtrl {

    @Autowired
    PersonService personService;

    /**
     * Search a person by his id
     * @param data map containing the id of the person
     * @return the person
     */
    @GetMapping(value= "/id")
    public ExtResponseEntity<Person> searchById(@RequestBody Map<String, String> data) {
        Person p = personService.getPersonById(data.get("id"));
        if (p == null) {
            return new ExtResponseEntity<>("Aucune personne correspondante a cet id !", HttpStatus.BAD_REQUEST);
        }
        return new ExtResponseEntity<>(p, HttpStatus.OK);
    }

    /**
     * Search a person by his name or his last name or his birthdate
     * @param data map containing the name, the last name and the birthdate of the person
     * @return list of persons corresponding to the search
     */
    @GetMapping(value= "/name")
    public ExtResponseEntity<List<Person>> searchByPersonalData(@RequestBody Map<String, String> data) {
        String firstName = Objects.equals(data.get("firstName"), "") ? null : data.get("firstName");
        String lastName = Objects.equals(data.get("lastName"), "") ? null : data.get("lastName");
        LocalDate birthDate = Objects.equals(data.get("birthDate"), "") ? null : LocalDate.parse(data.get("birthDate"));
        List<Person> p = personService.findAllByFirstNameAndLastNameAndBirthDate(
                firstName, lastName, birthDate
        );
        if (p == null) {
            return new ExtResponseEntity<>("Aucune personne correspondante a ce nom !", HttpStatus.BAD_REQUEST);
        }

        p.removeIf(person -> person.getFirstName().equals("unknown") && person.getLastName().equals("unknown"));
        return new ExtResponseEntity<>(p, HttpStatus.OK);
    }
}
