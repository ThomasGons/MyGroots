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


import static com.springboot.mygroots.model.FamilyGraph.FamilyRelation.*;

@RestController
public class MainController {


    @Autowired
    private AccountService accountService;

    @Autowired
    private PersonService personService;

    @Autowired
    private FamilyGraphService familyGraphService;


    @RequestMapping("/")
    public FamilyGraph root(){

//        Person p1 = new Person("John", "Doe", Person.Gender.MALE);
//        Person p2 = new Person("Jane", "Doe", Person.Gender.FEMALE);
//        Person p3 = new Person("Jo", "Doe", Person.Gender.MALE);
//        Person p4 = new Person("Joe", "Doe", Person.Gender.FEMALE);
//        personService.addPerson(p1);
//        personService.addPerson(p2);
//        personService.addPerson(p3);
//        personService.addPerson(p4);

//        accountService.addAccount(new Account("john@doe.com", p1));
//        accountService.addAccount(new Account("jane@doe.com", p2));
//        accountService.addAccount(new Account("jo@doe.com", p3));
//        accountService.addAccount(new Account("joe@doe.com", p4));

//        FamilyGraph fg = new FamilyGraph("Doe");
//        fg.addRelation(p1, p2, BROTHER);
//        fg.addRelation(p1, p3, SON);
//        fg.addRelation(p1, p4, SON);
//        fg.addRelation(p2, p1, SISTER);
//        fg.addRelation(p2, p3, DAUGHTER);
//        fg.addRelation(p2, p4, DAUGHTER);
//        fg.addRelation(p3, p1, FATHER);
//        fg.addRelation(p3, p2, FATHER);
//        fg.addRelation(p3, p4, HUSBAND);
//        fg.addRelation(p4, p1, MOTHER);
//        fg.addRelation(p4, p2, MOTHER);
//        fg.addRelation(p4, p3, WIFE);

//        familyGraphService.addFamilyGraph(fg);
        return familyGraphService.getByFamilyName("Doe");
    }

}
