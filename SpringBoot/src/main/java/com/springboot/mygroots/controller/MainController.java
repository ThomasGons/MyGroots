package com.springboot.mygroots.controller;

import com.springboot.mygroots.dto.FamilyTreeDTO;
import com.springboot.mygroots.model.Account;
import com.springboot.mygroots.model.FamilyTree;
import com.springboot.mygroots.model.Notif;
import com.springboot.mygroots.model.Person;
import com.springboot.mygroots.repository.NotifRepository;
import com.springboot.mygroots.service.*;
import com.springboot.mygroots.utils.Enumerations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.springboot.mygroots.utils.Enumerations.*;

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
        familyTreeService.getAllFamilyTrees().forEach(familyTree -> familyTreeService.removeFamilyTree(familyTree));
        accountService.getAllAccounts().forEach(account -> accountService.removeAccount(account));
        personService.getAllPersons().forEach(person -> personService.removePerson(person));

        Person john = new Person("john", "doe", Gender.MALE);
        Person dane  = new Person("dane", "doe", Gender.FEMALE);
        Person joe = new Person("joe", "doe", Gender.MALE);
        Person grandjoe = new Person("grandjoe", "doe", Gender.MALE);
        Person unclejoe = new Person("unclejoe", "doe",Gender.MALE);
        Person cousinjoe = new Person("cousinjoe","doe",Gender.FEMALE);
        personService.addPerson(john);
        personService.addPerson(dane);
        personService.addPerson(joe);
        personService.addPerson(grandjoe);
        personService.addPerson(unclejoe);
        personService.addPerson(cousinjoe);


        Account johnAcc = new Account("john@doe.com", john);
        Account daneAcc = new Account("dane@doe.com", dane);
        Account joeAcc = new Account("joe@doe.com", joe);
        Account grandjoeAcc = new Account("grandjoe@doe.com", grandjoe);
        Account unclejoeAcc = new Account("unclejoe@doe.com", unclejoe);
        Account cousinjoeAcc = new Account("cousinjoe@doe.com", cousinjoe);
        accountService.addAccount(johnAcc);
        accountService.addAccount(daneAcc);
        accountService.addAccount(joeAcc);
        accountService.addAccount(grandjoeAcc);
        accountService.addAccount(unclejoeAcc);
        accountService.addAccount(cousinjoeAcc);


        FamilyTree familyTree = new FamilyTree("Doe", johnAcc.getPerson());
        familyTreeService.saveFamilyTree(familyTree);
        johnAcc.setFamilyTree(familyTree);
        accountService.updateAccount(johnAcc);


        johnAcc.getFamilyTree().addFather(johnAcc.getPerson(), joeAcc.getPerson());
        johnAcc.getFamilyTree().addChild(joeAcc.getPerson(), daneAcc.getPerson());
        johnAcc.getFamilyTree().addFather(joeAcc.getPerson(), grandjoeAcc.getPerson());
        johnAcc.getFamilyTree().addChild(grandjoeAcc.getPerson(), unclejoeAcc.getPerson());
        johnAcc.getFamilyTree().addChild(unclejoeAcc.getPerson(), cousinjoeAcc.getPerson());

        familyTreeService.updateFamilyTree(johnAcc.getFamilyTree());
        accountService.updateAccount(johnAcc);

        List<Person> johnParents = johnAcc.getFamilyTree().getParents(johnAcc.getPerson());
        //print the list
        System.out.println("John's parents are: ");
        for (Person p : johnParents) {
            System.out.println(p.getName() + " " + p.getLastName());
        }

        List<Person> johnGrandParents = johnAcc.getFamilyTree().getGrandParents(johnAcc.getPerson());
        //print the list
        System.out.println("John's grandparents are: ");
        for (Person p : johnGrandParents) {
            System.out.println(p.getName() + " " + p.getLastName());
        }

        List<Person> grandjoeGrandChildren = johnAcc.getFamilyTree().getGrandChildren(grandjoeAcc.getPerson());
        //print the list
        System.out.println("Grandjoe's grandchildren are: ");
        for (Person p : grandjoeGrandChildren) {
            System.out.println(p.getName() + " " + p.getLastName());
        }

        List<Person> johnSiblings = johnAcc.getFamilyTree().getSiblings(johnAcc.getPerson());
        //print the list
        System.out.println("John's siblings are: ");
        for (Person p : johnSiblings) {
            System.out.println(p.getName() + " " + p.getLastName());
        }

        List<Person> johnUnclesAndAunts = johnAcc.getFamilyTree().getUnclesAndAunts(johnAcc.getPerson());
        //print the list
        System.out.println("John's uncles and aunts are: ");
        for (Person p : johnUnclesAndAunts) {
            System.out.println(p.getName() + " " + p.getLastName());
        }

        List<Person> johnCousins = johnAcc.getFamilyTree().getCousins(johnAcc.getPerson());
        //print the list
        System.out.println("John's cousins are: ");
        for (Person p : johnCousins) {
            System.out.println(p.getName() + " " + p.getLastName());
        }

        List<Person> joeNephewsAndNieces = johnAcc.getFamilyTree().getNephewsAndNieces(joeAcc.getPerson());
        //print the list
        System.out.println("Joe's nephews and nieces are: ");
        for (Person p : joeNephewsAndNieces) {
            System.out.println(p.getName() + " " + p.getLastName());
        }












    }

    @RequestMapping("/auth/activateAccount")
    public void activateAccount(@RequestParam String id) {
        accountService.activateAccount(id);
    }




}
