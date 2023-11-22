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
        notifService.getAllNotifs().forEach(notif -> notifService.removeNotif(notif));
        accountService.getAllAccounts().forEach(account -> accountService.removeAccount(account));
        personService.getAllPersons().forEach(person -> personService.removePerson(person));
        familyTreeService.getAllFamilyTrees().forEach(familyTree -> familyTreeService.removeFamilyTree(familyTree));
        Person john = new Person("john", "doe", Person.Gender.MALE);
        Person dane  = new Person("dane", "doe", Person.Gender.FEMALE);
        Person joe = new Person("joe", "doe", Person.Gender.MALE);
        personService.addPerson(john);
        personService.addPerson(dane);
        personService.addPerson(joe);

        Account johnAcc = new Account("john@doe.com", john);
        Account daneAcc = new Account("dane@doe.com", dane);
        Account joeAcc = new Account("joe@doe.com", joe);
        accountService.addAccount(johnAcc);
        accountService.addAccount(daneAcc);
        accountService.addAccount(joeAcc);

        FamilyTree familyTree = new FamilyTree("Doe", johnAcc.getPerson());
        familyTreeService.saveFamilyTree(familyTree);
        johnAcc.setFamilyTree(familyTree);


        johnAcc.getFamilyTree().addFather(johnAcc.getPerson(), joeAcc.getPerson());
        johnAcc.getFamilyTree().addChild(joeAcc.getPerson(), daneAcc.getPerson());

        familyTreeService.updateFamilyTree(johnAcc.getFamilyTree());
        accountService.updateAccount(johnAcc);



    }

    @RequestMapping("/auth/activateAccount")
    public void activateAccount(@RequestParam String id) {
        accountService.activateAccount(id);
    }




}
