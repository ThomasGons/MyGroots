package com.springboot.mygroots.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.springboot.mygroots.model.FamilyTree;
import com.springboot.mygroots.model.Person;
import com.springboot.mygroots.service.FamilyTreeService;
import com.springboot.mygroots.service.PersonService;
import com.springboot.mygroots.utils.Enumerations.Gender;

@RestController
@RequestMapping(value="/auth")
public class Auth {
    @Autowired
    private PersonService personService;
    
    @Autowired
    private FamilyTreeService familyTreeService;
    	
	@PostMapping(value= "/signup")
	public ResponseEntity<String> signUp(@RequestParam String email, @RequestParam String name, @RequestParam String lastName, @RequestParam LocalDate birthDate, @RequestParam String birthPlace, @RequestParam Gender gender, @RequestParam String nationality, @RequestParam String socialSecurityNumber) {
		try {
			System.out.println("SIGNUP");
			ResponseEntity<String> response = personService.signUp(email, name, lastName, birthDate, birthPlace, gender, nationality, socialSecurityNumber);
			Person p = personService.getPersonByNameAndLastName(name, lastName);
			FamilyTree ft = familyTreeService.getFamilyTreeByOwner(p);
			if (ft == null) {
				ft = new FamilyTree(name, p);
				familyTreeService.saveFamilyTree(ft);
			}
			return response;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<String>("{\"message\":\"Something wrong\"}", HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@PostMapping(value="/login")
	public ResponseEntity<String> login(@RequestParam String email, @RequestParam String password){
		try {
			return personService.login(email, password);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<String>("{\"message\":\"Something wrong\"}", HttpStatus.INTERNAL_SERVER_ERROR);
		
	}
	
	
}
