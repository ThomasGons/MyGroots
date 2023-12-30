package com.springboot.mygroots.controller;

import com.springboot.mygroots.model.Account;
import com.springboot.mygroots.model.Notif;
import com.springboot.mygroots.model.Person;
import com.springboot.mygroots.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value="/user")
@CrossOrigin(origins = "http://localhost:4200")
public class User {

    @Autowired
    AccountService accountService;

    @PostMapping(value= "/profile")
    public ResponseEntity<String> searchById(@RequestBody Map<String, String> data) {
        String id = data.get("id");
        System.out.println(id);
        Account a = accountService.getAccountById(id);
        if (a==null){
            return new ResponseEntity<String>("{\"errorMessage\":\"Aucun compte correspondant a cet id !\"}", HttpStatus.BAD_REQUEST);
        }
        Person p = a.getPerson();
        return new ResponseEntity<String> ("{\"id\":\""+p.getId()+"\", \"email\":\""+a.getEmail()
                +"\", \"firstName\":\""+p.getFirstName()+"\", \"lastName\":\""+p.getLastName()
                +"\", \"birthDate\":\""+p.getBirthDate().toString()+"\", \"gender\":\""+p.getGender()
                +"\", \"nationality\":\""+p.getNationality()+"\", \"socialSecurityNumber\":\""+p.getSocialSecurityNumber()+"\"}", HttpStatus.OK);
    }

    @GetMapping(value= "/notifications")
    public List<Notif> getNotifications(@RequestBody String email) {
        Account a = accountService.getAccountByEmail(email);
        return a.getNotifications();
    }
}
