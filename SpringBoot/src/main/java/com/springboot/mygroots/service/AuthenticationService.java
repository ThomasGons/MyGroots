package com.springboot.mygroots.service;

import java.time.LocalDate;
import java.util.Map;

import com.springboot.mygroots.utils.ExtResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.springboot.mygroots.model.Account;
import com.springboot.mygroots.model.Person;
import com.springboot.mygroots.utils.Utils;
import com.springboot.mygroots.utils.Enumerations.Gender;

@Service
public class AuthenticationService {

    @Autowired
    private PersonService personService;

    @Autowired
    private AccountService accountService;
    
    /**
     * Creation of a person and account, send a email to activate the account
     * @param email
     * @param firstName
     * @param lastName
     * @param birthDate
     * @param gender
     * @param nationality
     * @param socialSecurityNumber
     * @return message to indicated whether the sign up has been carried out correctly
     */
    public ExtResponseEntity<String> register(String email, String firstName, String lastName, LocalDate birthDate, Gender gender, String nationality, String socialSecurityNumber){
    	try{
    		Account checkExistingAccount = accountService.getAccountByEmail(email);
    		if (checkExistingAccount != null) {
        		return new ExtResponseEntity<>("Compte deja existant avec l'email "+email+".", HttpStatus.BAD_REQUEST);
    		}
			Person pers = personService.setPerson(firstName, lastName, birthDate, gender, nationality, socialSecurityNumber);
			personService.addPerson(pers);
			// Temporary password
			String passwordtmp = firstName.toLowerCase();
			Account acc = accountService.setAccount(email, passwordtmp, pers, "");
			accountService.addAccount(acc);
			// Send an email to activate the account
			accountService.sendAccountActivationMail(acc);
			return new ExtResponseEntity<>("Inscription réussie !", HttpStatus.OK);
    	} catch(Exception e){
    		e.printStackTrace();
    	}
		return new ExtResponseEntity<>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    /**
     * Attempt to log in with an email and password
     * @param email
     * @param password
     * @return Message to indicated whether the login has been carried out correctly and if the account is pending validation. 
     * When it is done correctly, it returns the current token, the account ID and the person's first name linked to this account.
     */
    public ExtResponseEntity<Map<String, String>> login(String email, String password){
    	try {
    		String password_input = Utils.encode(password);
    		Account account = accountService.getAccountByEmail(email);    		
    		if(account != null && account.getPassword().equals(password_input)){
    			if(account.isActive()){ 
    				String token = account.generateToken();
    				accountService.updateAccount(account);
					return new ExtResponseEntity<>(Map.of("token", token, "id", account.getId(), "firstName", account.getPerson().getFirstName()), HttpStatus.OK);
    			}else {
    				return new ExtResponseEntity<>("Compte en attente d'activation.", HttpStatus.BAD_REQUEST);
    			}
    		}
    	} catch(Exception e){
    		System.out.println(e);
    	}
		return new ExtResponseEntity<>("Email ou mot de passe incorrect !", HttpStatus.BAD_REQUEST);
    }
    
    /**
     * Reset the current token linked to the account to disconnect the user
     * @param token
     * @param id
     * @return Message to indicated whether the logout has been carried out correctly
     */
    public ExtResponseEntity<String> logout(String token, String accountId){
    	try {
    		Account acc = accountService.getAccountById(accountId);
			if (acc != null && acc.isAuthenticated(token)) {
				acc.resetToken();
				accountService.updateAccount(acc);
				return new ExtResponseEntity<>("Deconnexion reussie !", HttpStatus.OK);
			}
    	}catch(Exception e){
    		System.out.println(e);
    	}
		return new ExtResponseEntity<>("Echec de la deconnexion !", HttpStatus.BAD_REQUEST);
    }
    
    /**
     * Sending an e-mail if you forget your password
     * @param email
     * @return Message to indicated whether the new temporary token has been created correctly
     * When it is done correctly, return the account ID, the token and the first name of the person who forget his password.
     */
    public ExtResponseEntity<Map<String, String>> forgotPassword(String email) {
    	try {
    		Account acc = accountService.getAccountByEmail(email);
    		if (acc == null) {
				return new ExtResponseEntity<>("Aucun compte correspondant a cet email !", HttpStatus.BAD_REQUEST);
    		}
    		String token = acc.generateToken();
    		accountService.updateAccount(acc);
    		String firstName = acc.getPerson().getFirstName();
			return new ExtResponseEntity<>(Map.of("id", acc.getId(), "token", token, "firstName", firstName), HttpStatus.OK);
    	} catch (Exception e) {
    		System.out.println(e);
    	}
		return new ExtResponseEntity<>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    
    /**
	 * Creation of a new password
	 * @param accountId
	 * @param token
	 * @param newPassword
	 * @return message to indicated whether the changing of password has been carried out correctly
	 */
    public ExtResponseEntity<String> changePassword(String accountId, String token, String newPassword) {
    	try {
    		Account acc = accountService.getAccountById(accountId);
        	if (acc == null) {
				return new ExtResponseEntity<>("Aucun compte correspondant a cet id !", HttpStatus.BAD_REQUEST);
        	}
        	if (acc.isAuthenticated(token)) {
        		acc.setPassword(newPassword);
        		acc.resetToken();
        		accountService.updateAccount(acc);
				return new ExtResponseEntity<>("Modification du mot de passe reussie !", HttpStatus.OK);
        	}
			return new ExtResponseEntity<>("Impossible, donnees d'authentification invalides ou expirees !", HttpStatus.BAD_REQUEST);
    	} catch (Exception e) {
    		System.out.println(e);
    	}
		return new ExtResponseEntity<>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    
    /**
	 * Account activation by email
	 * @param accountId ID of the account to be activated
	 * @return message to indicated whether the activation of the account has been carried out correctly
	 * When the account is already activated or when the account was correctly activated, it return the email of the account.
	 */
    public ExtResponseEntity<String> activateAccount(String accountId) {
    	try {
    		Account acc = accountService.getAccountById(accountId);
    		if (acc == null) {
				return new ExtResponseEntity<>("Aucun compte correspondant a cet id !", HttpStatus.BAD_REQUEST);
    		}
    		if (!acc.isActive()) {
    			acc.activate();
    			accountService.updateAccount(acc);
				return new ExtResponseEntity<>("Activation du compte "+acc.getEmail()+" reussie !", HttpStatus.OK);
    		}
			return new ExtResponseEntity<>("Compte "+acc.getEmail()+" deja active !", HttpStatus.BAD_REQUEST);
    	} catch (Exception e) {
    		System.out.println(e);
    	}
		return new ExtResponseEntity<>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
