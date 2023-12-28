package com.springboot.mygroots.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.springboot.mygroots.Utils;
import com.springboot.mygroots.model.Account;
import com.springboot.mygroots.model.Person;
import com.springboot.mygroots.utils.Enumerations.Gender;

@Service
public class AuthenticationService {

    @Autowired
    private PersonService personService;

    @Autowired
    private AccountService accountService;
    
    
    public boolean isAuthenticated(String token) {
    	return true;
    }
    
    /**
     * 
     * @param email
     * @param firstName
     * @param lastName
     * @param birthDate
     * @param gender
     * @param nationality
     * @param socialSecurityNumber
     * @return
     */
    public ResponseEntity<String> register(String email, String firstName, String lastName, LocalDate birthDate, Gender gender, String nationality, String socialSecurityNumber){
    	try{
    		if (personService.validedSignUpPerson(firstName, lastName)) {
	    		Person p = personService.getPersonByFirstNameAndLastName(firstName, lastName);
	    		if (Objects.isNull(p)) {
	    			Person pers = personService.setPerson(firstName, lastName, birthDate, gender, nationality, socialSecurityNumber);
	    			personService.addPerson(pers);
	    			// Password temporaire a changer lors de la validation du compte
	    			String passwordtmp = firstName;
	    			Account acc = accountService.setAccount(email, passwordtmp, pers, "");
	    			System.out.println("DB password " + acc.getPassword());
	    			
	    			accountService.addAccount(acc);
	        		return new ResponseEntity<String>("{\"message\":\"Inscription reussie !\"}", HttpStatus.OK);
	    		}else {
	        		return new ResponseEntity<String>("{\"errorMessage\":\"Compte deja existant.\"}", HttpStatus.BAD_REQUEST);
	    		}
	    	}else {
	    		return new ResponseEntity<String>("{\"errorMessage\":\"Donnees invalides !\"}", HttpStatus.BAD_REQUEST);
	    	}
    	} catch(Exception e){
    		e.printStackTrace();
    	}return new ResponseEntity<String>("{\"errorMessage\":\"Something wrong\"}", HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    /**
     * 
     * @param email
     * @param password
     * @return
     */
    public ResponseEntity<String> login(String email, String password){
    	try {
    		String password_input = Utils.encode(password);
    		Account account = accountService.getAccountByEmail(email);
    		System.out.println("DB password " + account.getPassword());
    		System.out.println("User input  " + password_input);
    		
    		if(account != null && account.getPassword().equals(password_input)){
    			// a supp car pour test
    			account.activate();
    			
    			if(account.isActive() == true){ 
    				LocalDateTime currentDateTime = LocalDateTime.now();
    				String token = Utils.encode(currentDateTime.toString());
    				account.setToken(token);
    				accountService.updateAccount(account);
    				System.out.println(token);
    				System.out.println(account.getPerson().getId());
    				System.out.println("fin du login");
    				return new ResponseEntity<String>("{\"token\":\""+token+"\",\"id\":\""+account.getPerson().getId()+"\",\"firstName\":\""+account.getPerson().getFirstName()+"\"}", HttpStatus.OK);
    			}else {
    				return new ResponseEntity<String>("{\"errorMessage\":\"Compte en attente de verification.\"}", HttpStatus.BAD_REQUEST);
    			}
    		}
    	}catch(Exception e){
    		System.out.println(e);
    	}
		return new ResponseEntity<String>("{\"errorMessage\":\"Email ou mot de passe incorrect !\"}", HttpStatus.BAD_REQUEST);
    }
    
    /**
     * 
     * @param id
     * @return
     */
    public ResponseEntity<String> logout(String token, String id){
    	try {
    		Person p = personService.getPersonById(id);
    		if (p != null) {
    			Account acc = accountService.getAccountByPerson(p);
    			if (acc != null && acc.getToken().equals(token)) {
    				acc.setToken("");
    				accountService.updateAccount(acc);
        			return new ResponseEntity<String>("{\"message\": \"Deconnexion reussie !\"}", HttpStatus.OK);
    			}
    		}
    	}catch(Exception e){
    		System.out.println(e);
    	}
		return new ResponseEntity<String>("{\"errorMessage\":\"Echec de la deconnexion !\"}", HttpStatus.BAD_REQUEST);
    }
    
}
