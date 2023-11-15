package com.springboot.mygroots.controller;

import com.springboot.mygroots.dto.FamilyTreeDTO;
import com.springboot.mygroots.model.Account;
import com.springboot.mygroots.model.FamilyTree;
import com.springboot.mygroots.model.Person;
import com.springboot.mygroots.service.AccountService;
import com.springboot.mygroots.service.EmailService;
import com.springboot.mygroots.service.FamilyTreeService;
import com.springboot.mygroots.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class MainController {


    @Autowired
    private AccountService accountService;

    @Autowired
    private PersonService personService;


    @Autowired
    private FamilyTreeService familyTreeService;

    @RequestMapping(value= "/")
    public FamilyTreeDTO root() {
        return new FamilyTreeDTO(accountService.getFamilyTree("john@doe.com"));
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

        FamilyTree familyTree = new FamilyTree(p1.getLastName(), List.of(p1, p2, p3, p4));
        familyTree.addNode(p1, null, p4, p3);
        familyTree.addNode(p2, null, p4, p3);
        familyTree.addNode(p3, p4, null, null);
        familyTree.addNode(p4, p3, null, null);
        familyTreeService.saveFamilyTree(familyTree);

        Account a1 = accountService.getAccountByEmail("john@doe.com");
        a1.setFamilyTree(familyTree);
        accountService.updateAccount(a1);
    }

    @RequestMapping("/auth/activateAccount")
    public void activateAccount(@RequestParam String id) {
        accountService.activateAccount(id);
    }

}
