package com.springboot.mygroots.service;

import com.springboot.mygroots.model.Account;
import com.springboot.mygroots.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;


    public void addAccount(Account account){
        accountRepository.save(account);
    }

    public void updateAccount(Account account){
        accountRepository.save(account);
    }

    public List<Account> getAllAccounts(){
        return accountRepository.findAll();
    }

    public Account getAccountByEmail(String email){
        return accountRepository.getAccountByEmail(email);
    }


}
