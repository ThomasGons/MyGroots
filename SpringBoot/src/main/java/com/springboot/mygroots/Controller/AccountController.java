package com.springboot.mygroots.Controller;

import com.springboot.mygroots.Model.Account;
import com.springboot.mygroots.Model.Person;
import com.springboot.mygroots.Services.AccountService;
import com.springboot.mygroots.Services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountController {


    @Autowired
    private AccountService accountService;

    @Autowired
    private PersonService personService;


    @RequestMapping("/")
    public String root(){
       Person person = new Person("boug", "ouga", Person.Gender.FEMALE);
       personService.createPerson(person);
       accountService.createAccount(new Account("ouazefi",null));

        return "Hello World";
    }

}
