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
    public ExtResponseEntity<Account> searchById(@RequestBody Map<String, String> data) {
        String accountId = data.get("id");
        String token = data.get("token");
        Account acc = accountService.AuthenticatedAccount(token, accountId);
        if (acc == null) {
            return new ExtResponseEntity<>("Aucun compte correspondant a cet id !", HttpStatus.BAD_REQUEST);
        }
        return new ExtResponseEntity<>(acc, HttpStatus.OK);
    }

    /**
     * Get all the notifications of a user
     * @param data map containing the email of the user
     * @return list of notifications
     */
    @GetMapping(value = "/notifications")
    public ExtResponseEntity<List<Notif>> getNotifications(@RequestBody Map<String, String> data) {
        Account acc = accountService.AuthenticatedAccount(data.get("token"), data.get("id"));
        if (acc == null) {
            return new ExtResponseEntity<>("Aucun compte correspondant a cet id ou est authentifié !", HttpStatus.BAD_REQUEST);
        }
        return new ExtResponseEntity<>(acc.getNotifications(), HttpStatus.OK);
    }

    /**
     * Modify the profile of a user through a complete form
     * @param data map containing the id of the user and the new information
     * @return message to indicate whether the modification has been carried out correctly
     */
    @PostMapping(value = "/profile-modify")
    public ExtResponseEntity<?> modify(@RequestBody Map<String, String> data) {
        String accountId = data.get("id");
        Account acc = accountService.getAccountById(accountId);
        if (acc == null) {
            return new ExtResponseEntity<>("Aucun compte correspondant a cet id !", HttpStatus.BAD_REQUEST);
        }
        Person p = acc.getPerson();
        if (p == null) {
            return new ExtResponseEntity<>("Aucune personne correspondante a ce compte !", HttpStatus.BAD_REQUEST);
        }
        String email = data.get("email");
        String firstName = data.get("firstName");
        String lastName = data.get("lastName");
        LocalDate birthDate = LocalDate.parse(data.get("birthDate"));
        String nationality = data.get("nationality");
        String socialSecurityNumber = data.get("socialSecurityNumber");

        acc.setEmail(email);
        p.setFirstName(firstName);
        p.setLastName(lastName);
        p.setBirthDate(birthDate);
        p.setNationality(nationality);
        p.setSocialSecurityNumber(socialSecurityNumber);
        p.setGender(Enumerations.Gender.valueOf(data.get("gender")));

        accountService.updateAccount(acc);
        personService.updatePerson(p);
        return new ExtResponseEntity<>("Modification reussie !", HttpStatus.OK);
    }
}
