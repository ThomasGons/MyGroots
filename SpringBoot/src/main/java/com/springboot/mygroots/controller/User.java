package com.springboot.mygroots.controller;

import com.springboot.mygroots.model.Account;
import com.springboot.mygroots.model.FamilyTree;
import com.springboot.mygroots.model.Notif;
import com.springboot.mygroots.model.Person;
import com.springboot.mygroots.service.AccountService;
import com.springboot.mygroots.service.PersonService;
import com.springboot.mygroots.utils.Enumerations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value="/user")
@CrossOrigin(origins = "http://localhost:4200")
public class User {

    @Autowired
    AccountService accountService;
    @Autowired
    PersonService personService;

    /**
     * Post informations of the user's account
     * @param data Data of the user's session
     * @return all informations of the current account connected
     */
    @PostMapping(value = "/profile")
    public ResponseEntity<String> searchById(@RequestBody Map<String, String> data) {
        String accountId = data.get("id");
        String token = data.get("token");
        System.out.println(accountId);
        Account acc = accountService.AuthenticatedAccount(token, accountId);
		if ( acc != null) {
            Person p = acc.getPerson();
            return new ResponseEntity<String>("{\"id\":\"" + acc.getId() + "\", \"email\":\"" + acc.getEmail()
            + "\", \"firstName\":\"" + p.getFirstName() + "\", \"lastName\":\"" + p.getLastName()
            + "\", \"birthDate\":\"" + p.getBirthDate().toString() + "\", \"gender\":\"" + p.getGender()
            + "\", \"nationality\":\"" + p.getNationality() + "\", \"socialSecurityNumber\":\"" + p.getSocialSecurityNumber() + "\"}", HttpStatus.OK);
        }            
		return new ResponseEntity<String>("{\"errorMessage\":\"Aucun compte correspondant a cet id !\"}", HttpStatus.BAD_REQUEST);
    }

    //TODO : changer retour en ReponseEntity
    @GetMapping(value = "/notifications")
    public List<Notif> getNotifications(@RequestBody Map<String, String> data) {
    	Account acc = accountService.AuthenticatedAccount(data.get("token"), data.get("id"));
		if ( acc != null) {
			return acc.getNotifications();
		}
		return null;
    }

    /**
     * Post modified informations of the user's account
     * @param data Data of the user's session
     * @return all the informations of the current account connected with modifications
     */
    @PostMapping(value = "/profile-modify")
    public ResponseEntity<String> modify(@RequestBody Map<String, String> data) {
        String accountId = data.get("id");
        Account a = accountService.getAccountById(accountId);
        if (a == null) {
            return new ResponseEntity<String>("{\"errorMessage\":\"Aucun compte correspondant a cet id !\"}", HttpStatus.BAD_REQUEST);
        }
        Person p = a.getPerson();
        if (p == null) {
            return new ResponseEntity<String>("{\"errorMessage\":\"Aucune personne correspondante a ce compte !\"}", HttpStatus.BAD_REQUEST);
        }
        
        String email = data.get("email");
        String firstName = data.get("firstName");
        String lastName = data.get("lastName");
        LocalDate birthDate = LocalDate.parse(data.get("birthDate"));
        String nationality = data.get("nationality");
        String socialSecurityNumber = data.get("socialSecurityNumber");

        a.setEmail(email);
        p.setFirstName(firstName);
        p.setLastName(lastName);
        p.setBirthDate(birthDate);
        p.setNationality(nationality);
        p.setSocialSecurityNumber(socialSecurityNumber);
        p.setGender(Enumerations.Gender.valueOf(data.get("gender")));

        accountService.updateAccount(a);
        personService.updatePerson(p);
        return new ResponseEntity<String>("{\"message\":\"Vos informations ont été modifiées avec succès !\"}", HttpStatus.OK);
    }
}
