package com.springboot.mygroots.controller;

import com.springboot.mygroots.model.Account;
import com.springboot.mygroots.model.Notif;
import com.springboot.mygroots.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;

@RestController
@RequestMapping(value="/usr")
public class User {

    @Autowired
    AccountService accountService;

    @RequestMapping(value= "/profile")
    public Account searchById(@RequestBody String email) {
        return accountService.getAccountByEmail(email);
    }

    @RequestMapping(value= "/notifications")
    public List<Notif> getNotifications(@RequestBody String email) {
        Account a = accountService.getAccountByEmail(email);
        return a.getNotifications();
    }
}
