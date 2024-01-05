package com.springboot.mygroots.controller;

import com.springboot.mygroots.service.*;
import com.springboot.mygroots.utils.Enumerations.*;

import com.springboot.mygroots.dto.FamilyTreeDTO;
import com.springboot.mygroots.model.Account;
import com.springboot.mygroots.model.Email;
import com.springboot.mygroots.model.FamilyTree;
import com.springboot.mygroots.model.Person;
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

    @Autowired
    private AuthenticationService authenticationService;

    @RequestMapping(value= "/test")
    public FamilyTreeDTO root(@RequestBody Map<String, String> data) {
        FamilyTree ft = familyTreeService.getFamilyTreeById(data.get("id"));
        return new FamilyTreeDTO(ft);
    }

    /**
     * Initialize the database with some data
     */
/*    @RequestMapping(value= "/init")
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
    }*/


    @RequestMapping(value= "/init")
    public void init() {
        registerHeadless("lucas@moreau.com", "Lucas", "Moreau",
                LocalDate.of(1952, 2, 8), Gender.MALE, "France", "1000000000000"
        );

        registerHeadless("lucas@moreau.com", "Lucas", "Moreau",
                LocalDate.of(1952, 2, 8), Gender.MALE, "France", "1000000000000"
        );

        registerHeadless("anna@moreau.com", "Anna", "Moreau",
                LocalDate.of(1955, 5, 12), Gender.FEMALE, "France", "1000000000001"
        );

        registerHeadless("manon@despins.com", "Manin", "Despins",
                LocalDate.of(1954, 3, 15), Gender.FEMALE, "France", "1000000000002"
        );

        registerHeadless("lea@moreau.com", "Lea", "Moreau",
                LocalDate.of(1980, 8, 18), Gender.FEMALE, "France", "1000000000003"
        );

        registerHeadless("raphael@moreau.com", "Raphael", "Moreau",
                LocalDate.of(1982, 11, 22), Gender.MALE, "France", "1000000000004"
        );

        registerHeadless("emma@despins.com", "Emma", "Despins",
                LocalDate.of(1985, 12, 25), Gender.FEMALE, "France", "1000000000005"
        );

        registerHeadless("melvyn@despins.com", "Melvyn", "Despins",
                LocalDate.of(1987, 1, 28), Gender.MALE, "France", "1000000000006"
        );

        registerHeadless("gabriel@moreau.com", "Gabriel", "Moreau",
                LocalDate.of(2007, 4, 1), Gender.MALE, "France", "1000000000007"
        );

        registerHeadless("louis@moreau.com", "Louis", "Moreau",
                LocalDate.of(2009, 7, 4), Gender.MALE, "France", "1000000000008"
        );

        registerHeadless("alice@moreau.com", "Alice", "Moreau",
                LocalDate.of(2011, 10, 7), Gender.FEMALE, "France", "1000000000009"
        );

        registerHeadless("thomas@despins.com", "Thomas", "Despins",
                LocalDate.of(2008, 1, 10), Gender.MALE, "France", "1000000000010"
        );

        registerHeadless("matheo@despins.com", "Matheo", "Despins",
                LocalDate.of(2010, 4, 13), Gender.MALE, "France", "1000000000011"
        );

    }

    private void registerHeadless (
            String email, String firstName, String lastName, LocalDate birthDate,
            Gender gender, String nationality, String socialSecurityNumber
    ) {
        authenticationService.register(
                email, firstName, lastName, birthDate,
                gender, nationality, socialSecurityNumber
        );
        Account acc = accountService.getAccountByEmail(email);
        acc.activate();
        Person p = acc.getPerson();
        p.hasAccount();
        personService.updatePerson(p);
        FamilyTree ft = familyTreeService.getFamilyTreeByOwner(p);
        if (ft == null) {
            ft = new FamilyTree(lastName, p);
            familyTreeService.saveFamilyTree(ft);
        }
        acc.setFamilyTree(ft);
        accountService.updateAccount(acc);
    }
}
