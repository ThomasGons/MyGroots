package com.springboot.mygroots.dto;

import com.springboot.mygroots.model.Account;
import com.springboot.mygroots.model.Person;
import com.springboot.mygroots.utils.Enumerations;

public class AccountDTO {
    String accountId;
    String email;
    Enumerations.Visibility treeVisibility;
    Person person;

    public AccountDTO(Account account) {
        this.accountId = account.getId();
        this.email = account.getEmail();
        this.treeVisibility = account.getFamilyTree().getVisibility();
        this.person = account.getPerson();
    }

    public String getAccountId() {
        return accountId;
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