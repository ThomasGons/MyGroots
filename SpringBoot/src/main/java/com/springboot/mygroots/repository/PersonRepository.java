package com.springboot.mygroots.repository;

import com.springboot.mygroots.model.FamilyTree;
import com.springboot.mygroots.model.Person;

import java.time.LocalDate;

import org.springframework.data.mongodb.repository.MongoRepository;


public interface PersonRepository extends MongoRepository<Person, String> {

    Person getPersonByFirstNameAndLastNameAndBirthDate(String firstName, String lastName, LocalDate birthDate);
    
}
