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
        return personRepository.getPersonByFirstNameAndLastName(name, lastName);
    }

    public Person getPersonById(String id){
        return personRepository.findById(id).get();
    }

    public void removePerson(Person person){
        personRepository.delete(person);
    }

    
    public ResponseEntity<String> signUp(String email, String firstName, String lastName, LocalDate birthDate, Gender gender, String nationality, String socialSecurityNumber){
    	try{
    		if (this.validedSignUpPerson(firstName, lastName)) {
	    		Person p = personRepository.getPersonByFirstNameAndLastName(firstName, lastName);
	    		if (Objects.isNull(p)) {
	    			Person pers = this.setPerson(firstName, lastName, birthDate, gender, nationality, socialSecurityNumber);
	    			this.addPerson(pers);
	    			// creer un password temporaire avec son prenom
	    			StringBuilder passwordtmp = Utils.encode(lastName); 
	    			System.out.println(passwordtmp);
	    			Account acc = accountService.setAccount(email, passwordtmp.toString(), pers, null);
	    			accountService.addAccount(acc);
	        		return new ResponseEntity<String>("{\"message\":\"Inscription reussie\"}", HttpStatus.OK);
	    		}else {
	        		return new ResponseEntity<String>("{\"message\":\"Compte deja existant\"}", HttpStatus.BAD_REQUEST);
	    		}
	    	}else {
	    		return new ResponseEntity<String>("{\"message\":\"Invalid Data\"}", HttpStatus.BAD_REQUEST);
	    	}
    	} catch(Exception e){
    		e.printStackTrace();
    	}return new ResponseEntity<String>("{\"message\":\"Something wrong\"}", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private boolean validedSignUpPerson(String firstName, String lastName) {
    	if (firstName != null && lastName != null) {
    		return true;
    	}
    	return false;
    }
    
    private Person setPerson(String firstName, String lastName, LocalDate birthDate, Gender gender, String nationality, String socialSecurityNumber) {
    	Person p = new Person(firstName, lastName, gender);
    	p.setBirthDate(birthDate);
    	p.setNationality(nationality);
    	p.setSocialSecurityNumber(socialSecurityNumber);
    	return p;
    }
    
    public ResponseEntity<String> login(String email, String password){
    	try {
    		StringBuilder password_input = Utils.encode(password);
    		Account account = accountRepository.getAccountByEmail(email);
    		if(account.getPassword().equals(password_input.toString())){
    			// a supp car pour test
    			account.activate();
    			if(account.isActive() == true){ 
    				LocalDateTime currentDateTime = LocalDateTime.now();
    				String token = Utils.encode(currentDateTime.toString()).toString();
    				account.setToken(token);
    				return new ResponseEntity<String>("{'token':"+token+",'id':"+account.getPerson().getId()+",'firstName':"+account.getPerson().getFirstName()+"}", HttpStatus.OK);
    			}else {
    				return new ResponseEntity<String>("{\"message\":\"Compte en attente de verification\"}", HttpStatus.BAD_REQUEST);
    			}
    		}
    	}catch(Exception e){
    		System.out.println(e);
    	}
		return new ResponseEntity<String>("{\"message\":\"Email ou mot de passe incorrect\"}", HttpStatus.BAD_REQUEST);

    }
    
    public ResponseEntity<String> logout(String id){
    	try {
    		System.out.println(id);
    		Person p = getPersonById(id);
    		System.out.println(p);
    		if (p != null) {
    			Account a = accountRepository.getAccountByPerson(p);
    			a.setToken(null);
    			return new ResponseEntity<String>("{'message': 'Deconnexion reussie'}", HttpStatus.OK);
    		}
    	}catch(Exception e){
    		System.out.println(e);
    	}
		return new ResponseEntity<String>("{\"message\":\"Echec deconnexion\"}", HttpStatus.BAD_REQUEST);
    }
}
