package com.springboot.mygroots.Services;

import com.springboot.mygroots.Model.Person;
import com.springboot.mygroots.Repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;


    public void createPerson(Person person){
        personRepository.save(person);
    }


    public Person getPerson(int id){
        return personRepository.findById(id).get();
    }

    public void updatePerson(Person person){
        personRepository.save(person);
    }

    public void deletePerson(int id){
        personRepository.deleteById(id);
    }

    public List<Person> getAllPersons(){
        return personRepository.findAll();
    }
    
    
    
    


}
