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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
public class MainController {


    @Autowired
    private AccountService accountService;

    @Autowired
    private PersonService personService;

    @Autowired
    private EmailService emailService;
    
    @Autowired
    private FamilyTreeService familyTreeService;

    @RequestMapping(value= "/")
    public FamilyTreeDTO root() {
    	return new FamilyTreeDTO(accountService.getFamilyTree("john@doe.com"));
    }

    @RequestMapping(value="/inbox") // localhost:8080/inbox?name=John&lastName=Doe
    public List<Email> inbox (@RequestParam String name, @RequestParam String lastName, LocalDate birthdate){
        Person p = personService.getPersonByFirstNameAndLastNameAndBirthDate(name, lastName, birthdate);
        return emailService.getInbox(p);
    }

    // create a init method to create a sample family graph
    @RequestMapping(value= "/init")
    public void init() {

        //delete all database
        familyTreeService.getAllFamilyTrees().forEach(familyTree -> familyTreeService.removeFamilyTree(familyTree));
        accountService.getAllAccounts().forEach(account -> accountService.removeAccount(account));
        personService.getAllPersons().forEach(person -> personService.removePerson(person));


        Person p1 = new Person("John", "Doe", Gender.MALE);
        Person p2 = new Person("Jane", "Doe", Gender.FEMALE);
        Person p3 = new Person("Jo", "Doe", Gender.MALE);
        Person p4 = new Person("Joe", "Doe", Gender.FEMALE);
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
        ft1.addChild(p1, p4);
        
        familyTreeService.saveFamilyTree(ft1);
        
        Account a1 = accountService.getAccountByEmail("john@doe.com");
        a1.setFamilyTree(ft1);
        accountService.updateAccount(a1);
        
        
    }
}
