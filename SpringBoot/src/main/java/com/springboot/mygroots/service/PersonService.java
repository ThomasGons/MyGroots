package com.springboot.mygroots.service;

import com.springboot.mygroots.model.FamilyTree;
import com.springboot.mygroots.Utils;
import com.springboot.mygroots.model.Account;
import com.springboot.mygroots.model.Person;
import com.springboot.mygroots.repository.AccountRepository;
import com.springboot.mygroots.repository.PersonRepository;
import com.springboot.mygroots.utils.Enumerations.Gender;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
public class PersonService {
	
    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private AccountRepository accountRepository;
    
    @Autowired
    private AccountService accountService;

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

    
    public ResponseEntity<String> signUp(String email, String name, String lastName, LocalDate birthDate, String birthPlace, Gender gender, String nationality, String socialSecurityNumber){
    	try{
    		if (this.validedSignUpPerson(name, lastName)) {
	    		Person p = personRepository.getPersonByNameAndLastName(name, lastName);
	    		if (Objects.isNull(p)) {
	    			Person pers = this.setPerson(name, lastName, birthDate, birthPlace, gender, nationality, socialSecurityNumber);
	    			this.addPerson(pers);
	    			// creer un password temporaire avec son prenom
	    			StringBuilder passwordtmp = Utils.encode(name); 
	    			System.out.println(passwordtmp);
	    			Account acc = accountService.setAccount(email, passwordtmp.toString(), pers);
	    			accountService.addAccount(acc);
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
    
    private Person setPerson(String name, String lastName, LocalDate birthDate, String birthPlace, Gender gender, String nationality, String socialSecurityNumber) {
    	Person p = new Person(name, lastName, gender);
    	p.setBirthDate(birthDate);
    	p.setBirthPlace(birthPlace);
    	p.setNationality(nationality);
    	p.setSocialSecurityNumber(socialSecurityNumber);
    	return p;
    }
    
    public ResponseEntity<String> login(String email, String password){
    	try {
    		StringBuilder password_input = Utils.encode(password);
    		Account account = accountRepository.getAccountByEmail(email);
    		if(account.getPassword().equals(password_input.toString())){
    			if(account.isActive() == true){ 
    				LocalDateTime currentDateTime = LocalDateTime.now();
    				String token = Utils.encode(currentDateTime.toString()).toString();
    				return new ResponseEntity<String>("{\"message\":\""+token+"\"}", HttpStatus.OK);
    			}else {
    				return new ResponseEntity<String>("{\"message\":\"Wait for verification by email\"}", HttpStatus.BAD_REQUEST);
    			}
    		}
    	}catch(Exception e){
    		System.out.println(e);
    	}
		return new ResponseEntity<String>("{\"message\":\"Login failed : bad credentials\"}", HttpStatus.BAD_REQUEST);

    }
}
