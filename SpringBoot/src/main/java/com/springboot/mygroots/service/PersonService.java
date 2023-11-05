package com.springboot.mygroots.service;

import com.springboot.mygroots.model.FamilyGraph;
import com.springboot.mygroots.model.Person;
import com.springboot.mygroots.model.Person.Gender;
import com.springboot.mygroots.repository.FamilyGraphRepository;
import com.springboot.mygroots.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.aggregation.VariableOperators.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;
    
    @Autowired
    private FamilyGraphRepository familyGraphRepository;

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
    
    public ResponseEntity<String> signUp(String name, String lastName, LocalDate birthDate, LocalDate deathDate, String birthPlace, String deathPlace, Gender gender){
    	try{
    		if (this.validedSignUpPerson(name, lastName)) {
	    		Person p = personRepository.getPersonByNameAndLastName(name, lastName);
	    		if (Objects.isNull(p)) {
	    			//Person pers = this.setInfoPerson(name, lastName, birthDate, deathDate, birthPlace, deathPlace, gender);
	    			//this.addPerson(pers);
	    			
	        		return new ResponseEntity<String>("{\"message\":\"Succesfully registered\"}", HttpStatus.OK);
	    		}else {
	        		return new ResponseEntity<String>("{\"message\":\"Already exist\"}", HttpStatus.BAD_REQUEST);
	    		}
	    	}else {
	    		return new ResponseEntity<String>("{\"message\":\"Invalid Data\"}", HttpStatus.BAD_REQUEST);
	    	}
    	} catch(Exception e){
    		e.printStackTrace();
    	}return new ResponseEntity<String>("{\"message\":\"Something wrong\"}", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private boolean validedSignUpPerson(String name, String lastName) {
    	if (name != null && lastName != null) {
    		return true;
    	}
    	return false;
    }
    
    private Person setInfoPerson(String name, String lastName, LocalDate birthDate, LocalDate deathDate, String birthPlace, String deathPlace, Gender gender) {
    	Person p = new Person(name, lastName, gender);
    	p.setBirthDate(birthDate);
    	p.setBirthPlace(birthPlace);
    	p.setDeathDate(deathDate);
    	p.setDeathPlace(deathPlace);
    	return p;
    }
}
