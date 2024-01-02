package com.springboot.mygroots.controller;

import com.springboot.mygroots.model.Account;
import com.springboot.mygroots.model.Notif;
import com.springboot.mygroots.model.Person;
import com.springboot.mygroots.service.AccountService;
import com.springboot.mygroots.service.PersonService;
import com.springboot.mygroots.utils.Enumerations;
import com.springboot.mygroots.utils.ExtResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * Controller for the user
 */
@RestController
@RequestMapping(value="/user")
@CrossOrigin(origins = "http://localhost:4200")
public class UserCtrl {

    @Autowired
    AccountService accountService;
    @Autowired
    PersonService personService;

    /**
     * Get the profile of a user
     * @param data map containing the id of the user
     * @return the profile of the user
     */
    @PostMapping(value = "/profile")
    public ExtResponseEntity<Map<String, ?>> searchById(@RequestBody Map<String, String> data) {
        String id = data.get("id");
        Account a = accountService.getAccountById(id);
        if (a == null) {
            return new ExtResponseEntity<>("Aucun compte correspondant a cet id !", HttpStatus.BAD_REQUEST);
        }
        return new ExtResponseEntity<>(Map.of("email", a.getEmail(), "person", a.getPerson()), HttpStatus.OK);
    }

    /**
     * Get all the notifications of a user
     * @param data map containing the email of the user
     * @return list of notifications
     */
    @GetMapping(value = "/notifications")
    public ExtResponseEntity<List<Notif>> getNotifications(@RequestBody Map<String, String> data) {
        Account a = accountService.getAccountByEmail(data.get("email"));
        if (a == null) {
            return new ExtResponseEntity<>("Aucun compte correspondant a cet email !", HttpStatus.BAD_REQUEST);
        }
        return new ExtResponseEntity<>(a.getNotifications(), HttpStatus.OK);
    }

    /**
     * Modify the profile of a user through a complete form
     * @param data map containing the id of the user and the new information
     * @return message to indicate whether the modification has been carried out correctly
     */
    @PostMapping(value = "/profile-modify")
    public ExtResponseEntity<?> modify(@RequestBody Map<String, String> data) {
        String id = data.get("id");

        Account a = accountService.getAccountById(id);

        if (a == null) {
            return new ExtResponseEntity<>("Aucun compte correspondant a cet id !", HttpStatus.BAD_REQUEST);
        }
        Person p = a.getPerson();
        if (p == null) {
            return new ExtResponseEntity<>("Aucune personne correspondante a ce compte !", HttpStatus.BAD_REQUEST);
        }
        String email = data.get("email");
        String firstname = data.get("firstName");
        String lastname = data.get("lastName");
        LocalDate birthDate = LocalDate.parse(data.get("birthDate"));
        String nationality = data.get("nationality");
        String socialSecurityNumber = data.get("socialSecurityNumber");


        a.setEmail(email);
        p.setFirstName(firstname);
        p.setLastName(lastname);
        p.setBirthDate(birthDate);
        p.setNationality(nationality);
        p.setSocialSecurityNumber(socialSecurityNumber);
        p.setGender(Enumerations.Gender.valueOf(data.get("gender")));

        accountService.updateAccount(a);
        personService.updatePerson(p);
        return new ExtResponseEntity<>("Modification reussie !", HttpStatus.OK);
    }
}
