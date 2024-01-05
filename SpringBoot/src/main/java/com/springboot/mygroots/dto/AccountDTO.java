package com.springboot.mygroots.dto;

import com.springboot.mygroots.model.Account;
import com.springboot.mygroots.model.Person;
import com.springboot.mygroots.utils.Enumerations;

public class AccountDTO {
    String account_id;
    String email;
    Enumerations.Visibility treeVisibility;
    Person person;

    public AccountDTO(Account account) {
        this.account_id = account.getId();
        this.email = account.getEmail();
        this.treeVisibility = account.getFamilyTree().getVisibility();
        this.person = account.getPerson();
    }

    public AccountDTO(String account_id, String email, Enumerations.Visibility treeVisibility, Person person) {
        this.account_id = account_id;
        this.email = email;
        this.treeVisibility = treeVisibility;
        this.person = person;
    }

    public String getAccount_id() {
        return account_id;
    }

    public String getEmail() {
        return email;
    }

    public Enumerations.Visibility getTreeVisibility() {
        return treeVisibility;
    }

    public Person getPerson() {
        return person;
    }
}
