package com.springboot.mygroots.controller;

import com.springboot.mygroots.model.Account;
import com.springboot.mygroots.model.Notif;
import com.springboot.mygroots.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;

@RestController
@RequestMapping(value="/user")
@CrossOrigin(origins = "http://localhost:4200")
public class User {

    @Autowired
    AccountService accountService;

    @GetMapping(value= "/profile")
    public Account searchById(@RequestBody String email) {
        return accountService.getAccountByEmail(email);
    }

    @GetMapping(value= "/notifications")
    public List<Notif> getNotifications(@RequestBody String email) {
        Account a = accountService.getAccountByEmail(email);
        return a.getNotifications();
    }
}
