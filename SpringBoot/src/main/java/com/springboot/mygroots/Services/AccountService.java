package com.springboot.mygroots.Services;

import com.springboot.mygroots.Model.Account;
import com.springboot.mygroots.Repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;


    public void createAccount(Account account){
        accountRepository.save(account);
    }


    public Account getAccount(int id){
        return accountRepository.findById(id).get();
    }

    public void updateAccount(Account account){
        accountRepository.save(account);
    }

    public void deleteAccount(int id){
        accountRepository.deleteById(id);
    }

    public List<Account> getAllAccounts(){
        return accountRepository.findAll();
    }




}
