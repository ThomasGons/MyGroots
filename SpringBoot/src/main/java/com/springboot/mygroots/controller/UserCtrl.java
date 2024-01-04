package com.springboot.mygroots.controller;

import com.springboot.mygroots.model.Account;
import com.springboot.mygroots.model.Notif;
import com.springboot.mygroots.model.Person;
import com.springboot.mygroots.service.AccountService;
import com.springboot.mygroots.service.PersonService;
import com.springboot.mygroots.utils.Enumerations;
import com.springboot.mygroots.utils.Enumerations.Gender;
import com.springboot.mygroots.utils.ExtResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
    public ExtResponseEntity<Map<String, ?>> getProfile(@RequestBody Map<String, String> data) {
        String accountId = data.get("accountId");
        String token = data.get("token");
        Account acc = accountService.AuthenticatedAccount(token, accountId);
        if (acc == null) {
            return new ExtResponseEntity<>("Aucun compte correspondant a cet id !", HttpStatus.BAD_REQUEST);
        }
        return new ExtResponseEntity<>(Map.of("email", acc.getEmail(), "person", acc.getPerson()), HttpStatus.OK);
    }
    
    /**
     * Modify the profile of a user through a complete form
     * @param data map containing the id of the user and the new information
     * @return message to indicate whether the modification has been carried out correctly
     */
    @PostMapping(value = "/profile-modify")
    public ExtResponseEntity<?> profileModify(@RequestBody Map<String, String> data) {
        Account acc = accountService.AuthenticatedAccount(data.get("token"), data.get("id"));
        if (acc == null) {
            return new ExtResponseEntity<>("Aucun compte correspondant a cet id !", HttpStatus.BAD_REQUEST);
        }
        Person p = acc.getPerson();
        if (p == null) {
            return new ExtResponseEntity<>("Aucune personne correspondante a ce compte !", HttpStatus.BAD_REQUEST);
        }
        try {
        	String email = data.get("email");
            String firstname = data.get("firstName");
            String lastname = data.get("lastName");
            LocalDate birthDate = LocalDate.parse(data.get("birthDate"));
            Gender gender = Gender.valueOf(data.get("gender"));
            String nationality = data.get("nationality");
            String socialSecurityNumber = data.get("socialSecurityNumber");

            if (email != null)
            	acc.setEmail(email);
            if (firstname != null)
            	p.setFirstName(firstname);
            if (lastname != null)
            	p.setLastName(lastname);
    		if (birthDate != null)
    			p.setBirthDate(birthDate);
    		if (gender != null)
    			p.setGender(gender);
    		if (nationality != null)
    			p.setNationality(nationality);
    		if (socialSecurityNumber != null)
    			p.setSocialSecurityNumber(socialSecurityNumber);

            accountService.updateAccount(acc);
            personService.updatePerson(p);
            return new ExtResponseEntity<>("Modification reussie !", HttpStatus.OK);
        } catch (Exception e) {
        	e.printStackTrace();
        }
        return new ExtResponseEntity<>("Echec de la modifications des données !", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Get all the notifications of a user
     * @param data map containing the email of the user
     * @return list of notifications
     */
    @PostMapping(value = "/notifs")
    public ExtResponseEntity<List<Notif>> getNotifs(@RequestBody Map<String, String> data) {
        Account acc = accountService.AuthenticatedAccount(data.get("token"), data.get("accountId"));
        if (acc == null) {
            return new ExtResponseEntity<>("Aucun compte correspondant a cet id ou est authentifié !", HttpStatus.BAD_REQUEST);
        }
        return new ExtResponseEntity<>(acc.getNotifs(), HttpStatus.OK);
    }

}
