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

    @PostMapping(value = "/profile")
    public ResponseEntity<String> searchById(@RequestBody Map<String, String> data) {
        String id = data.get("id");
        System.out.println(id);
        Account a = accountService.getAccountById(id);
        if (a == null) {
            return new ResponseEntity<String>("{\"errorMessage\":\"Aucun compte correspondant a cet id !\"}", HttpStatus.BAD_REQUEST);
        }
        Person p = a.getPerson();
        return new ResponseEntity<String>("{\"id\":\"" + a.getId() + "\", \"email\":\"" + a.getEmail()
                + "\", \"firstName\":\"" + p.getFirstName() + "\", \"lastName\":\"" + p.getLastName()
                + "\", \"birthDate\":\"" + p.getBirthDate().toString() + "\", \"gender\":\"" + p.getGender()
                + "\", \"nationality\":\"" + p.getNationality() + "\", \"socialSecurityNumber\":\"" + p.getSocialSecurityNumber() + "\"}", HttpStatus.OK);
    }

    @GetMapping(value = "/notifications")
    public List<Notif> getNotifications(@RequestBody String email) {
        Account a = accountService.getAccountByEmail(email);
        return a.getNotifications();
    }


    @PostMapping(value = "/profile-modify")
    public ResponseEntity<String> modify(@RequestBody Map<String, String> data) {
        String id = data.get("id");

        Account a = accountService.getAccountById(id);
        Person p = a.getPerson();

        if (a == null) {
            return new ResponseEntity<String>("{\"errorMessage\":\"Aucun compte correspondant a cet id !\"}", HttpStatus.BAD_REQUEST);
        }
        if (p == null) {
            return new ResponseEntity<String>("{\"errorMessage\":\"Aucune personne correspondante a ce compte !\"}", HttpStatus.BAD_REQUEST);
        }


        String email = data.get("email");
        String firstname = data.get("firstName");
        String lastname = data.get("lastName");
        LocalDate birthDate = LocalDate.parse(data.get("birthDate"));
        String nationality = data.get("nationality");
        String socialsecurity = data.get("socialSecurity");


        a.setEmail(email);
        p.setFirstName(firstname);
        p.setLastName(lastname);
        p.setBirthDate(birthDate);
        p.setNationality(nationality);
        p.setSocialSecurityNumber(socialsecurity);
        p.setGender(Enumerations.Gender.valueOf(data.get("gender")));

        accountService.updateAccount(a);
        personService.updatePerson(p);
        return new ResponseEntity<String>("{\"message\":\"Vos informations ont été modifiées avec succès !\"}", HttpStatus.OK);
    }
}
