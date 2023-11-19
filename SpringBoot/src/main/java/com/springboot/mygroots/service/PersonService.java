package com.springboot.mygroots.service;

import com.springboot.mygroots.model.FamilyTree;
import com.springboot.mygroots.model.Person;
import com.springboot.mygroots.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    
    public Person getPersonByNameAndLastName(String name, String lastName){
        return personRepository.getPersonByNameAndLastName(name, lastName);
    }

    public Person getPersonById(String id){
        return personRepository.findById(id).get();
    }

    public void removePerson(Person person){
        personRepository.delete(person);
    }

}
