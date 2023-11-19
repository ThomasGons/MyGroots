package com.springboot.mygroots.controller;

import com.springboot.mygroots.dto.FamilyTreeDTO;
import com.springboot.mygroots.model.Account;
import com.springboot.mygroots.model.FamilyTree;
import com.springboot.mygroots.model.Notif;
import com.springboot.mygroots.model.Person;
import com.springboot.mygroots.repository.NotifRepository;
import com.springboot.mygroots.service.*;
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
    private NotifService notifService;


    @Autowired
    private FamilyTreeService familyTreeService;

    @RequestMapping(value= "/")
    public FamilyTreeDTO root() {
        return new FamilyTreeDTO(accountService.getFamilyTree("john@doe.com"));
    }

    // create a init method to create a sample family graph
    @RequestMapping(value= "/init")
    public void init() {
        accountService.getAllAccounts().forEach(account -> accountService.removeAccount(account));
        personService.getAllPersons().forEach(person -> personService.removePerson(person));
        familyTreeService.getAllFamilyTrees().forEach(familyTree -> familyTreeService.removeFamilyTree(familyTree));
        notifService.getAllNotifs().forEach(notif -> notifService.deleteNotif(notif));
        Person p1 = new Person("jon","doe", Person.Gender.MALE);
        Person p2 = new Person("dana", "doe", Person.Gender.FEMALE);
        personService.addPerson(p1);
        personService.addPerson(p2);
        Account a1 = new Account("lucas.cotot@gmail.com",p1);
        Account a2 = new Account("lazerf@gmail.com",p2);
        accountService.addAccount(a1);
        accountService.addAccount(a2);

        Notif n1 = new Notif(a1,a2, Notif.NotifType.DEMAND_ADDTOFAMILY);
        notifService.saveNotif(n1);
        a1.getNotifs().add(n1);
        accountService.updateAccount(a1);


    }

    @RequestMapping("/auth/activateAccount")
    public void activateAccount(@RequestParam String id) {
        accountService.activateAccount(id);
    }


}
