package com.springboot.mygroots.repository;

import com.springboot.mygroots.model.FamilyTree;
import com.springboot.mygroots.model.Person;

import org.springframework.data.mongodb.repository.MongoRepository;


public interface PersonRepository extends MongoRepository<Person, String> {

    Person getPersonByFirstNameAndLastName(String firstName, String lastName);
    
}
