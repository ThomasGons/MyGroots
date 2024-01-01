package com.springboot.mygroots.controller;

import java.time.LocalDate;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.mygroots.model.Account;
import com.springboot.mygroots.model.FamilyTree;
import com.springboot.mygroots.model.Person;
import com.springboot.mygroots.service.FamilyTreeService;
import com.springboot.mygroots.service.PersonService;
import com.springboot.mygroots.service.AccountService;
import com.springboot.mygroots.service.AuthenticationService;
import com.springboot.mygroots.utils.Enumerations.Gender;

@RestController
@RequestMapping(value="/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthenticationController {	
    @Autowired
    private AuthenticationService authenticationService;
    
    @Autowired
    private AccountService accountService;
    
    @Autowired
    private FamilyTreeService familyTreeService;
    	
	/**
	 * Register a new user into the database. Creation of an account and a person. Potentially create a tree if his family tree does not exist.
	 * @param data Table of informations
	 * @return Message to indicated whether the registration has been carried out correctly
	 */
    @PostMapping(value= "/register")
	public ResponseEntity<String> register(@RequestBody Map<String, String> data){
		try {
			ResponseEntity<String> response = authenticationService.register(
				data.get("email"),
				data.get("firstName"),
				data.get("lastName"),
				LocalDate.parse(data.get("birthDate")),
				Gender.valueOf(data.get("gender")),
				data.get("nationality"),
				data.get("socialSecurityNumber")
			);
			Account acc = accountService.getAccountByEmail(data.get("email"));
			Person p = acc.getPerson();
			FamilyTree ft = familyTreeService.getFamilyTreeByOwner(p);
			if (ft == null) {
				ft = new FamilyTree(data.get("lastName"), p);
				familyTreeService.saveFamilyTree(ft); 
			}
			return response;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<String>("{\"errorMessage\":\"Une erreur s'est produite.\"}", HttpStatus.INTERNAL_SERVER_ERROR);	
	}
	
	/**
	 * Connection to the account with the email and the password
	 * @param account_login Search for an account that matches the email address and password
	 * @return Message to indicated whether the login has been carried out correctly
	 * When it is done correctly, it returns the current token, the account ID and the person's first name linked to this account.
	 */
	@PostMapping(value="/login")
	public ResponseEntity<String> login(@RequestBody Account account_login){
		try {
			return authenticationService.login(account_login.getEmail(), account_login.getPassword());
		}catch(Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<String>("{\"errorMessage\":\"Une erreur s'est produite.\"}", HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	/**
	 * Account logout : reset the token of the current connected account
	 * @param data Account data containing account ID and token. 
	 * @return Message to indicated whether the logout has been carried out correctly
	 */
	@PostMapping(value="/logout")
	public ResponseEntity<String> logout(@RequestBody Map<String, String> data){
		try {
			return authenticationService.logout(data.get("token"), data.get("id"));
		}catch(Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<String>("{\"errorMessage\":\"Une erreur s'est produite.\"}", HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	/**
	 * Sending an e-mail if you forget your password
	 * @param data account data containing email
	 * @return message to indicated whether the changing of password has been carried out correctly
	 * When it is done correctly, return the account ID, the token and the first name of the person who forget his password.
	 */
	@PostMapping(value="/forgot-password")
	public ResponseEntity<String> forgotPassword(@RequestBody Map<String, String> data) {
		try {
			return authenticationService.forgotPassword(data.get("email"));
		} catch(Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<String>("{\"errorMessage\":\"Une erreur s'est produite.\"}", HttpStatus.INTERNAL_SERVER_ERROR); 
	}
	
	/**
	 * Creation of a new password
	 * @param data account data containing ID, token and new password
	 * @return message to indicated whether the changing of password has been carried out correctly
	 */
	@PutMapping(value="/change-password")
	public ResponseEntity<String> changePassword(@RequestBody Map<String, String> data) {
		try {
			return this.authenticationService.changePassword(data.get("id"), data.get("token"), data.get("newPassword"));
		} catch(Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<String>("{\"errorMessage\":\"Une erreur s'est produite.\"}", HttpStatus.INTERNAL_SERVER_ERROR); 
	}
	
	/**
	 * Account activation by email
	 * @param accountId ID of the account to be activated
	 * @return message to indicated whether the activation of the account has been carried out correctly
	 * When the account is already activated or when the account was correctly activated, it return the email of the account.
	 */
	@PostMapping(value="/activate-account/{accountId}")
	public ResponseEntity<String> activateAccount(@PathVariable("accountId") String accountId) {
		try {
			return authenticationService.activateAccount(accountId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<String>("{\"errorMessage\":\"Une erreur s'est produite.\"}", HttpStatus.INTERNAL_SERVER_ERROR); 
	}
}
