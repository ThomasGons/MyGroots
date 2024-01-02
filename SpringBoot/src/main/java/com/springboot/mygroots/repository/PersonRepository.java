package com.springboot.mygroots.repository;

import com.springboot.mygroots.model.Person;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface PersonRepository extends MongoRepository<Person, String> {

    @Query("{ $or: [ { 'firstName': ?0 }, { 'lastName': ?1 }, { 'birthDate': ?2 } ] }")
    List<Person> findAllPersonByFirstNameAndLastNameAndBirthDate(String firstName, String lastName, LocalDate birthDate);
    
}
