package com.springboot.mygroots.controller;

import com.springboot.mygroots.model.Account;
import com.springboot.mygroots.model.FamilyGraph;
import com.springboot.mygroots.model.Person;
import com.springboot.mygroots.service.AccountService;
import com.springboot.mygroots.service.FamilyGraphService;
import com.springboot.mygroots.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AccountController {


    @Autowired
    private AccountService accountService;

    @Autowired
    private PersonService personService;

    @Autowired
    private FamilyGraphService familyGraphService;


    @RequestMapping("/")
    public List<FamilyGraph> root(){
        Person p1 = new Person("John", "Doe", Person.Gender.MALE);
        Person p2 = new Person("Jane", "Doe", Person.Gender.FEMALE);
        personService.addPerson(p1);
        personService.addPerson(p2);

        Account a1 = new Account("john@doe.com", p1);
        Account a2 = new Account("jane@doe.com", p2);

        accountService.addAccount(a1);
        accountService.addAccount(a2);

        FamilyGraph fg = new FamilyGraph();
        fg.addRelation(p1, p2, FamilyGraph.FamilyRelation.BROTHER);
        familyGraphService.addFamilyGraph(fg);

        return familyGraphService.getAllFamilyGraphs();
    }

}
