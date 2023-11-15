package com.springboot.mygroots.controller;

import com.springboot.mygroots.model.Account;
import com.springboot.mygroots.model.Email;
import com.springboot.mygroots.model.FamilyGraph;
import com.springboot.mygroots.model.Person;
import com.springboot.mygroots.service.AccountService;
import com.springboot.mygroots.service.EmailService;
import com.springboot.mygroots.service.FamilyGraphService;
import com.springboot.mygroots.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;

import static com.springboot.mygroots.model.FamilyGraph.FamilyRelation.*;

@RestController
public class MainController {


    @Autowired
    private AccountService accountService;

    @Autowired
    private PersonService personService;

    @Autowired
    private FamilyGraphService familyGraphService;

    @Autowired
    private EmailService emailService;


    @RequestMapping(value= "/")
    public void root() {
        Person p = new Person("Thomas", "Gons", Person.Gender.MALE);
        personService.addPerson(p);

        Account a = new Account("gons.thomas@gmail.com", p);
        accountService.addAccount(a);
    }

    // create a init method to create a sample family graph
    @RequestMapping(value= "/init")
    public void init() {
        Person p1 = new Person("John", "Doe", Person.Gender.MALE);
        Person p2 = new Person("Jane", "Doe", Person.Gender.FEMALE);
        Person p3 = new Person("Jo", "Doe", Person.Gender.MALE);
        Person p4 = new Person("Joe", "Doe", Person.Gender.FEMALE);
        personService.addPerson(p1);
        personService.addPerson(p2);
        personService.addPerson(p3);
        personService.addPerson(p4);

        accountService.addAccount(new Account("john@doe.com", p1));
        accountService.addAccount(new Account("jane@doe.com", p2));
        accountService.addAccount(new Account("jo@doe.com", p3));
        accountService.addAccount(new Account("joe@doe.com", p4));

        FamilyGraph fg = new FamilyGraph("Doe");

        fg.addOwner(p1);
        fg.addOwner(p2);

        familyGraphService.addFamilyGraph(fg);
        
        FamilyGraph r_fg = familyGraphService.getFamilyGraphByOwner(p1);
        
        familyGraphService.addRelation(r_fg, p1, p2, BROTHER);
        familyGraphService.addRelation(r_fg, p1, p3, SON);
        familyGraphService.addRelation(r_fg, p1, p4, SON);
        familyGraphService.addRelation(r_fg, p2, p1, SISTER);
        familyGraphService.addRelation(r_fg, p2, p3, DAUGHTER);
        familyGraphService.addRelation(r_fg, p2, p4, DAUGHTER);
        familyGraphService.addRelation(r_fg, p3, p1, FATHER);
        familyGraphService.addRelation(r_fg, p3, p2, FATHER);
        familyGraphService.addRelation(r_fg, p3, p4, HUSBAND);
        familyGraphService.addRelation(r_fg, p4, p1, MOTHER);
        familyGraphService.addRelation(r_fg, p4, p2, MOTHER);
        familyGraphService.addRelation(r_fg, p4, p3, WIFE);

        familyGraphService.updateFamiyGraph(r_fg);
    }

    @RequestMapping("/auth/activateAccount")
    public void activateAccount(@RequestParam String id) {
        accountService.activateAccount(id);
    }

}
