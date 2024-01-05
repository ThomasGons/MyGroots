package com.springboot.mygroots.controller;

import com.springboot.mygroots.utils.Enumerations.*;

import com.springboot.mygroots.dto.FamilyTreeDTO;
import com.springboot.mygroots.model.Account;
import com.springboot.mygroots.model.Email;
import com.springboot.mygroots.model.FamilyTree;
import com.springboot.mygroots.model.Person;
import com.springboot.mygroots.service.AccountService;
import com.springboot.mygroots.service.EmailService;
import com.springboot.mygroots.service.FamilyTreeService;
import com.springboot.mygroots.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * Controller for the back only requests (test purposes)
 */
@RestController
public class MiscCtrl {

    @Autowired
    private AccountService accountService;

    @Autowired
    private PersonService personService;

    @Autowired
    private FamilyTreeService familyTreeService;

    @RequestMapping(value= "/test")
    public FamilyTreeDTO root(@RequestBody Map<String, String> data) {
        FamilyTree ft = familyTreeService.getFamilyTreeById(data.get("id"));
        return new FamilyTreeDTO(ft);
    }

    /**
     * Initialize the database with some data
     */
    @RequestMapping(value= "/init")
    public void init() {

        //delete all database
        familyTreeService.getAllFamilyTrees().forEach(familyTree -> familyTreeService.removeFamilyTree(familyTree));
        accountService.getAllAccounts().forEach(account -> accountService.removeAccount(account));
        personService.getAllPersons().forEach(person -> personService.removePerson(person));


        Person p1 = new Person("John", "Doe", Gender.MALE);
        p1.setBirthDate(LocalDate.of(1970, 1, 18));
        Person p2 = new Person("Jane", "Doe", Gender.FEMALE);
        p2.setBirthDate(LocalDate.of(1972, 8, 13));
        Person p3 = new Person("Jo", "Doe", Gender.MALE);
        p3.setBirthDate(LocalDate.of(1995, 5, 5));
        Person p4 = new Person("Joe", "Doe", Gender.FEMALE);
        p4.setBirthDate(LocalDate.of(1997, 12, 24));
        personService.addPerson(p1);
        personService.addPerson(p2);
        personService.addPerson(p3);
        personService.addPerson(p4);

        accountService.addAccount(new Account("john@doe.com", "john", p1, null));
        accountService.addAccount(new Account("jane@doe.com", "jane", p2, null));
        accountService.addAccount(new Account("jo@doe.com", "jo", p3, null));
        accountService.addAccount(new Account("joe@doe.com", "joe", p4, null));
        
        
        FamilyTree ft1 = new FamilyTree("Doe", p1);
        
        ft1.addChild(p1, p3);
        ft1.addMother(p3, p2);
        
        familyTreeService.saveFamilyTree(ft1);
        
        Account a1 = accountService.getAccountByEmail("john@doe.com");
        a1.setFamilyTree(ft1);
        accountService.updateAccount(a1);
    }
}
