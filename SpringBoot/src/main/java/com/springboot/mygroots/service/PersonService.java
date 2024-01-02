package com.springboot.mygroots.service;

import com.springboot.mygroots.utils.Utils;
import com.springboot.mygroots.model.Account;
import com.springboot.mygroots.model.Person;
import com.springboot.mygroots.repository.PersonRepository;
import com.springboot.mygroots.utils.Enumerations.Gender;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class PersonService {
	
    @Autowired
    private PersonRepository personRepository;

    public void addPerson(Person person){
        personRepository.save(person);
    }

    public void updatePerson(Person person){
        personRepository.save(person);
    }

    public List<Person> getAllPersons(){
        return personRepository.findAll();
    }
    
    public Person getPersonById(String id){
    	return personRepository.findById(id).get();
    }
    
    public List<Person> findAllByFirstNameAndLastNameAndBirthDate(String firstName, String lastName, LocalDate birthDate){
        return personRepository.findAllPersonByFirstNameAndLastNameAndBirthDate(firstName, lastName, birthDate);
    }

    public void removePerson(Person person){
        personRepository.delete(person);
    }

    /**
     * Creation of a Person
     * @param firstName
     * @param lastName
     * @param birthDate
     * @param gender
     * @param nationality
     * @param socialSecurityNumber
     * @return person created
     */
    protected Person setPerson(String firstName, String lastName, LocalDate birthDate, Gender gender, String nationality, String socialSecurityNumber) {
    	Person p = new Person(firstName, lastName, gender);
    	p.setBirthDate(birthDate);
    	p.setNationality(nationality);
    	p.setSocialSecurityNumber(socialSecurityNumber);
    	return p;
    }
}