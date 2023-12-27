package com.springboot.mygroots.controller;

import java.time.LocalDate;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.mygroots.model.Account;
import com.springboot.mygroots.model.FamilyTree;
import com.springboot.mygroots.model.Person;
import com.springboot.mygroots.service.AccountService;
import com.springboot.mygroots.service.FamilyTreeService;
import com.springboot.mygroots.service.PersonService;
import com.springboot.mygroots.utils.Enumerations.Gender;

@RestController
@RequestMapping(value="/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthentificationController {	
    @Autowired
    private PersonService personService;
    
    @Autowired
    private FamilyTreeService familyTreeService;
    	
	@PostMapping(value= "/register")
	/**
	 * Creation of a account and a person
	 * @param data table of informations
	 * @return message to indicated whether the signup has been carried out correctly
	 */
	public ResponseEntity<String> signUp(@RequestBody Map<String, Object> data){
		try {
			System.out.println("SIGNUP");
			ResponseEntity<String> response = personService.signUp((String) data.get("email"), (String) data.get("FirstName"), (String) data.get("LastName"),LocalDate.parse((String) data.get("BirthDate")), Gender.valueOf((String) data.get("Gender")), (String) data.get("Nationality"), (String) data.get("SocialSecurityNumber"));
			Person p = personService.getPersonByNameAndLastName((String) data.get("FirstName"), (String) data.get("LastName"));
			System.out.println();
			FamilyTree ft = familyTreeService.getFamilyTreeByOwner(p);
			if (ft == null) {
				ft = new FamilyTree((String) data.get("LastName"), p);
				familyTreeService.saveFamilyTree(ft); 
			}
			return response;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<String>("{\"message\":\"Something wrong\"}", HttpStatus.INTERNAL_SERVER_ERROR);	
	}
	
	@PostMapping(value="/login")
	/**
	 * Connection to the account with the email and the password
	 * @param account_login search for an account that matches the email address and password
	 * @return message to indicated whether the login has been carried out correctly
	 */
	public ResponseEntity<String> login(@RequestBody Account account_login){
		System.out.println("test login");
		try {
			return personService.login(account_login.getEmail(), account_login.getPassword());
		}catch(Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<String>("{\"message\":\"Something wrong\"}", HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@PostMapping(value="/logout/{id}")
	/**
	 * Account logout
	 * @param id current account ID
	 * @return message to indicated whether the logout has been carried out correctly
	 */
	public ResponseEntity<String> logout(@PathVariable("id") String id){
		try {
			System.out.println(id);
			return personService.logout(id);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<String>("{\"message\":\"Something wrong\"}", HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
}
