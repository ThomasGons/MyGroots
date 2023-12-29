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
    		Account checkExistingAccount = accountService.getAccountByEmail(email);
    		if (checkExistingAccount != null) {
        		return new ResponseEntity<String>("{\"errorMessage\":\"Compte deja existant avec l'email "+email+".\"}", HttpStatus.BAD_REQUEST);
    		}
    		
			Person pers = personService.setPerson(firstName, lastName, birthDate, gender, nationality, socialSecurityNumber);
			personService.addPerson(pers);
			// Password temporaire a changer lors de la validation du compte
			String passwordtmp = firstName.toLowerCase();
			Account acc = accountService.setAccount(email, passwordtmp, pers, "");
			accountService.addAccount(acc);
			// Activation mail
			accountService.sendAccountActivationMail(acc);
    		return new ResponseEntity<String>("{\"message\":\"Inscription reussie !\"}", HttpStatus.OK);
	    	
    	} catch(Exception e){
    		e.printStackTrace();
    	}
    	return new ResponseEntity<String>("{\"errorMessage\":\"Something went wrong\"}", HttpStatus.INTERNAL_SERVER_ERROR);
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
    		if(account != null && account.getPassword().equals(password_input)){
    			if(account.isActive()){ 
    				String token = account.generateToken();
    				accountService.updateAccount(account);
    				return new ResponseEntity<String>("{\"token\":\""+token+"\",\"id\":\""+account.getPerson().getId()+"\",\"firstName\":\""+account.getPerson().getFirstName()+"\"}", HttpStatus.OK);
    			}else {
    				return new ResponseEntity<String>("{\"errorMessage\":\"Compte en attente d'activation.\"}", HttpStatus.BAD_REQUEST);
    			}
    		}
    	}catch(Exception e){
    		System.out.println(e);
    	}
		return new ResponseEntity<String>("{\"errorMessage\":\"Email ou mot de passe incorrect !\"}", HttpStatus.BAD_REQUEST);
    }
    
    /**
     * 
     * @param token
     * @param id
     * @return
     */
    public ResponseEntity<String> logout(String token, String id){
    	try {
    		Person p = personService.getPersonById(id);
    		if (p != null) {
    			Account acc = accountService.getAccountByPerson(p);
    			if (acc != null && acc.isAuthenticated(token)) {
    				acc.resetToken();
    				accountService.updateAccount(acc);
        			return new ResponseEntity<String>("{\"message\": \"Deconnexion reussie !\"}", HttpStatus.OK);
    			}
    		}
    	}catch(Exception e){
    		System.out.println(e);
    	}
		return new ResponseEntity<String>("{\"errorMessage\":\"Echec de la deconnexion !\"}", HttpStatus.BAD_REQUEST);
    }
    
    
    /**
     * 
     */
    public ResponseEntity<String> forgotPassword(String email) {
    	try {
    		Account acc = accountService.getAccountByEmail(email);
    		if (acc == null) {
    			return new ResponseEntity<String>("{\"errorMessage\":\"Aucun compte correspondant a ce mail !\"}", HttpStatus.BAD_REQUEST);
    		}
    		String token = acc.generateToken();
    		accountService.updateAccount(acc);
    		String firstName = acc.getPerson().getFirstName();
    		return new ResponseEntity<String>("{\"id\":\""+acc.getId()+"\", \"token\":\""+token+"\", \"firstName\":\""+firstName+"\"}", HttpStatus.OK);
    	} catch (Exception e) {
    		System.out.println(e);
    	}
    	return new ResponseEntity<String>("{\"errorMessage\":\"Something went wrong\"}", HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    
    /**
     * 
     */
    public ResponseEntity<String> changePassword(String accountId, String token, String newPassword) {
    	try {
    		Account acc = accountService.getAccountById(accountId);
        	if (acc == null) {
        		return new ResponseEntity<String>("{\"errorMessage\":\"Aucun compte correspondant a cet id !\"}", HttpStatus.BAD_REQUEST);
        	}
        	if (acc.isAuthenticated(token)) {
        		acc.setPassword(newPassword);
        		acc.resetToken();
        		accountService.updateAccount(acc);
        		return new ResponseEntity<String>("{\"message\":\"Modification du mot de passe reussie !\"}", HttpStatus.OK);
        	}
        	return new ResponseEntity<String>("{\"errorMessage\":\"Impossible, donnees d'authentification invalides ou expirees !\"}", HttpStatus.BAD_REQUEST);
    	} catch (Exception e) {
    		System.out.println(e);
    	}
    	
    	return new ResponseEntity<String>("{\"errorMessage\":\"Something went wrong\"}", HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    
    /**
     * 
     */
    public ResponseEntity<String> activateAccount(String accountId) {
    	try {
    		Account acc = accountService.getAccountById(accountId);
    		if (acc == null) {
    			return new ResponseEntity<String>("{\"errorMessage\":\"Aucun compte correspondant a cet id !\"}", HttpStatus.BAD_REQUEST);
    		}
    		if (!acc.isActive()) {
    			acc.activate();
    			accountService.updateAccount(acc);
    			return new ResponseEntity<String>("{\"message\":\"Activation du compte "+acc.getEmail()+" reussie !\"}", HttpStatus.OK);
    		}
    		return new ResponseEntity<String>("{\"message\":\"Compte "+acc.getEmail()+" deja active !\"}", HttpStatus.OK);
    	} catch (Exception e) {
    		System.out.println(e);
    	}
    	return new ResponseEntity<String>("{\"errorMessage\":\"Something went wrong\"}", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
