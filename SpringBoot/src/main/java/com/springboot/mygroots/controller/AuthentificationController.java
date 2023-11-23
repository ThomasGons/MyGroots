package com.springboot.mygroots.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.VariableOperators.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.mygroots.model.Account;
import com.springboot.mygroots.model.FamilyGraph;
import com.springboot.mygroots.model.Person;
import com.springboot.mygroots.model.Person.Gender;
import com.springboot.mygroots.service.AccountService;
import com.springboot.mygroots.service.FamilyGraphService;
import com.springboot.mygroots.service.PersonService;

@RestController
@RequestMapping(value="/auth")
public class AuthentificationController {	
    @Autowired
    private AccountService accountService;

    @Autowired
    private PersonService personService;
    
    @Autowired
    private FamilyGraphService familyGraphService;
	
	//creer une personne et set tous les champs
	@PostMapping(value= "/signup")
	public ResponseEntity<String> signUp(@RequestParam String email, @RequestParam String name, @RequestParam String lastName, @RequestParam LocalDate birthDate, @RequestParam String birthPlace, @RequestParam Gender gender, @RequestParam String nationality, @RequestParam String socialSecurityNumber) {
		try {
			System.out.println("SIGNUP");
			ResponseEntity<String> response = personService.signUp(email, name, lastName, birthDate, birthPlace, gender, nationality, socialSecurityNumber);
			FamilyGraph fg = familyGraphService.getByFamilyName(name);
			Person p = personService.getPersonByNameAndLastName(name, lastName);
			if (fg == null) {
				fg = new FamilyGraph(name);
				System.out.println(fg);
				fg.addOwner(p);
				familyGraphService.addFamilyGraph(fg);
			}else {
				if (familyGraphService.getFamilyGraphByOwner(p) == null) {
					fg.addOwner(p);
					familyGraphService.updateFamilyGraph(fg);
				}
			}
			return response;
		}catch(Exception e) {
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
