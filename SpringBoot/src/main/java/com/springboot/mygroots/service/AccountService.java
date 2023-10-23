package com.springboot.mygroots.service;

import com.springboot.mygroots.model.Account;
import com.springboot.mygroots.model.Person;
import com.springboot.mygroots.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private JavaMailSender javaMailSender;

    public void addAccount(Account account) {
        accountRepository.save(account);
        sendAccountActivationMail(account.getEmail(), account);
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

    private void sendAccountActivationMail(String to, Account account) {
        Person person = account.getPerson();
        SimpleMailMessage message = new SimpleMailMessage();
        String confirmationLink = "http://localhost:8080/auth/activateAccount?id=" + account.getId();
        message.setTo(to);
        message.setSubject("MyGroots Account Activation");
        message.setText("Hello" + person.getName() + " " + person.getLastName() + " ! Welcome to MyGroots.\n" +
                "You have successfully created an account. Your temporary password is" + ".\n" +
                " To activate your account, please follow the link below:\n" +
                confirmationLink);
        javaMailSender.send(message);
    }

    public void activateAccount(String id){
        Account account = accountRepository.getAccountById(id);
        account.activate();
        accountRepository.save(account);
    }
}
