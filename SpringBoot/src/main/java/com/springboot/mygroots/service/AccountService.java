package com.springboot.mygroots.service;

import com.springboot.mygroots.model.Account;
import com.springboot.mygroots.model.FamilyTree;
import com.springboot.mygroots.model.Notif;
import com.springboot.mygroots.model.Person;
import com.springboot.mygroots.repository.AccountRepository;
import com.springboot.mygroots.repository.NotifRepository;
import com.springboot.mygroots.utils.Utils;

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

    @Autowired
    private NotifRepository notifRepository;

    public void addAccount(Account account) {
        accountRepository.save(account);
        //sendAccountActivationMail(account.getEmail(), account);
    }

    public void updateAccount(Account account){
        accountRepository.save(account);
    }

    public List<Account> getAllAccounts(){
        return accountRepository.findAll();
    }

    public Account getAccountById(String accountId) {
    	return accountRepository.getAccountById(accountId);
    }
    
    public Account getAccountByEmail(String email){
        return accountRepository.getAccountByEmail(email);
    }
    
    public Account getAccountByPerson(Person person) {
    	return accountRepository.getAccountByPerson(person);
    }

    protected void sendAccountActivationMail(Account account) {
        Person person = account.getPerson();
        SimpleMailMessage message = new SimpleMailMessage();
        String confirmationLink = "http://localhost:4200/auth/activate-account/" + account.getId();
        message.setTo(account.getEmail());
        message.setSubject("MyGroots Account Activation");
        message.setText("Hello " + person.getFirstName() + " " + person.getLastName() + " ! Welcome to MyGroots.\n" +
                "You have successfully created an account. Your temporary password is : " + person.getFirstName().toLowerCase() + "\n" +
                "To activate your account, please follow the link below :\n" +
                confirmationLink);
        javaMailSender.send(message);
    }

    public void removeAccount(Account account){
        accountRepository.delete(account);
    }

    public void activateAccount(String accountId){
        Account account = accountRepository.getAccountById(accountId);
        account.activate();
        accountRepository.save(account);
    }

    public FamilyTree getFamilyTree(String email){
        return accountRepository.getAccountByEmail(email).getFamilyTree();
    }

    public Account setAccount(String email, String password, Person person, String token) {
    	Account a = new Account(email, Utils.encode(password),  person, token);
    	return a;
    }
    
    public Account AuthentificatedUser(String token, String accountId) {
		Account acc = getAccountById(accountId);
		if (acc != null && acc.isAuthenticated(token)) {
			return acc;
		}
		return null;
    }
}
